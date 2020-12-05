package com.example.demo.service;

import com.example.demo.domain.Covid;
import com.example.demo.domain.Location;
import com.example.demo.domain.User;
import com.example.demo.repository.CovidRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CovidService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CovidRepository covidRepository;

    @Autowired
    UserRepository userRepository;

    public String data_api() throws Exception {
        Calendar cal = Calendar.getInstance();
        Date today=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYYMMdd");
        cal.setTime(today);
        cal.add(Calendar.DATE,-1);

        String serviceKey = "tufyZ6VLBILq5TuIblvCdAMD2fFepYFp29pal5%2BESdXrpluiCGBGv4ucJzMHTEKc%2FTQvdZDrlv1lILc8s8H88g%3D%3D";
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode(simpleDateFormat.format(cal.getTime()), "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode(simpleDateFormat.format(today), "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        JSONObject jsonObject = XML.toJSONObject(sb.toString());

        JSONObject items = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//        System.out.println("Convert result : "+items.getString("item"));

        return items.toString();
    }

    public void AlertCovid(int cid, HashMap<String, String> map) {

//        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timestamp date=Timestamp.valueOf(map.get("date"));
        System.out.println(map.get("date"));
//        try {
//            date = fm.parse(map.get("Date"));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        // -2~2일간 확진자 이동경로
        List<Covid> covoidList = covidRepository.getCovidByCovid_numAndCreated_atBetween(cid,new Timestamp(date.getTime()));

        List<String> alertUser = new ArrayList<>();

        // 모든 회원 목록
        List<User> totalUser = userRepository.findAll();

        boolean checked = false;
        for (int i = 0; i < covoidList.size(); i++) {
            Covid covid = covoidList.get(i);

            // 테스트 용 로그
//		System.out.println("확진자 하나 가져옴");
//		System.out.println(covid);

            for (int j = 0; j < totalUser.size(); j++) {
                checked = false;
                for (int l = 0; l < alertUser.size(); l++) {
                    if (alertUser.get(l) == totalUser.get(j).getId()) {
                        checked = true;
                        break;
                    }
                }
                if (!checked) {
                    // 모든 회원 목록에서 하나를 가져와확진자 이동날짜에 이동한 회원의 이동 경로
                    List<Location> user_locList = locationRepository
                            .getLocationsByUserIdAndCreated_atBetween(totalUser.get(j).getId(), covid.getCreated_at());

                    // 해당 하는 유저 목록 없으면 실행 안함
                    if (!user_locList.isEmpty()) {

                        // 유저 이동경로따라 확진자와 겹치는 부분 확인
                        for (int k = 0; k < user_locList.size(); k++) {
                            // 거리 비교 6피트 = 1.8288m
//						System.out.println("가능성 체크 시작");

                            double covidLatitude = Math.round(covid.getLatitude() * 100000) / 100000.0;
                            double covidLongtitude = Math.round(covid.getLongitude() * 100000) / 100000.0;

                            double userLatitude = Math.round(user_locList.get(k).getLatitude() * 100000) / 100000.0;
                            double userLongtitude = Math.round(user_locList.get(k).getLongitude() * 100000) / 100000.0;

                            double dis = distance(covidLatitude, covidLongtitude, userLatitude, userLongtitude);

                            System.out.println("거리:" + dis);
                            if (dis <= 100) { // 겹치는 부분이 있을 경우
                                alertUser.add(user_locList.get(k).getUser().getId());// 메세지 보낸 유저목록에 포함
                                // 문자 발송
                                sendMessage(totalUser.get(j).getPhone(),
                                        user_locList.get(k).getCreated_at().toString(),
                                        getAddress(Double.toString(user_locList.get(k).getLatitude()),
                                                Double.toString(user_locList.get(k).getLongitude())));
                                // TEST console 출력
                                /*
                                 * System.out.println(totalUser.get(j).getPhoneNum() + "(으)로 전송\n" +
                                 * user_locList.get(k).getCreated_at().toString() + " " +
                                 * getAddress(Double.toString(user_locList.get(k).getLatitude()),
                                 * Double.toString(user_locList.get(k).getLongitude())) +
                                 * "에서 감염가능성이 확인 되었습니다.");
                                 */

                                k = user_locList.size();// 해당 유저에 대한 경로 탐색 종료
                            }
                        }
                    }
                }
            }
        }
    }

    public void sendMessage(String phoneNum, String date, String address) {

        /**
         * @class ExampleSend
         * @brief This sample code demonstrate how to send sms through CoolSMS Rest API
         *        PHP
         */

        String api_key = "NCSCDR2E1ZHENOI2";
        String api_secret = "ZKPIREW75GAMJDKKNYMCMUTGIFCUMXCT";
        Message coolsms = new Message(api_key, api_secret);
        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNum);
        params.put("from", "01037256448");
        params.put("type", "SMS");
        params.put("text", "2020-07-08, 서울시 중랑구 상도동 상도로68길 12에서 감염가능성이 확인되었습니다. http://localhost:8080/kakao에서 확인하세요");
        params.put("app_version", "나는여기"); // application name and version

        try {
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject)coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
//?????????????????????????????????????????????????????????
//	public User getUser(String uid) {
//		String phoneNum;
//		User user = new User();
//		return user = userRepository.getUserById(uid);
//
//	}

    public String getAddress(String lat, String lon) {

        try {
            BufferedReader in = null;
            JsonParser parser = new JsonParser();

            URL urlObject = new URL("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat
                    + "&longitude=" + lon + "&localityLanguage=ko");
            HttpsURLConnection con = (HttpsURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");

            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            Object obj = parser.parse(sb.toString());
            JsonObject jsonObj = (JsonObject) obj;
            String city = jsonObj.get("city").toString();
            String locality = jsonObj.get("locality").toString();

            System.out.println("시 : " + city);
            System.out.println("동 : " + locality);

            return city + " " + locality;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//
//	    	}
//
//
        }
        return "알수 없는 지역";
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1609.344;//미터
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public List<Covid> getCovidLocations(Timestamp date)
    {
        List<Covid> locations=covidRepository.getLocationsByCreated_atEquals(date);
        System.out.println(locations.toString());
        return locations;
    }
}

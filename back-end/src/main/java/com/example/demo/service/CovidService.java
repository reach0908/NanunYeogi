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
import java.text.ParseException;
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
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);

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
//        System.out.println(sb.toString());

        JSONObject jsonObject = XML.toJSONObject(sb.toString());

        JSONObject items = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//        System.out.println("Convert result : "+items.getString("item"));

        return items.toString();
    }

    public void AlertCovid(int cid, HashMap<String, String> map) {

    	// Date format을 "yyyy-MM-dd" 형식으로 지정
    			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    			Date date = new Date();
    			//map 형식으로 받아온 String type의 date를 미리지정한 Date fromat으로 변형
    			try {
    				date = fm.parse(map.get("Date"));
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			// 확진일 기준 -2~+3일간 확진자 이동경로를 불러옴
    			// 쿼리 : select * from covid where covid_id= cid and created_at between date_format(date_add( date,interval -2 day),'%Y-%m-%d')
    			//		and date_format(date_add(date,interval 3 day),'%Y-%m-%d')
    			List<Covid> covoidList = covidRepository.getCovidByCovidIdAndCreated_atBetween(cid, new Timestamp(date.getTime()));

    			List<String> alertUser = new ArrayList<>();
    			List<User> totalUser = new ArrayList<>();
    			
    			// 모든 회원 목록을 불러옴
    			totalUser = userRepository.findAll();
    			//프로시저 동안 유저한테 메세지를 보냈는지 체크 
    			boolean checked = false;
    			//확진자 이동경로가 남았을때
    			for (int i = 0; i < covoidList.size(); i++) {
    				//확진자의 이동경로 정보 하나를 불러옴
    				Covid covid = covoidList.get(i);
    				//현재 불러온 유저리스트의 모든 유저에 대해서 메세지 전송여부 판단 후 감염가능성 판단 시작
    				for (int j = 0; j < totalUser.size(); j++) {
    					checked = false;
    					for (int l = 0; l < alertUser.size(); l++) {
    						if (alertUser.get(l) == totalUser.get(j).getId()) {
    							checked = true;
    						}
    					}
    					if (!checked) {
    						// 모든 회원 목록에서 하나를 가져와 확진자 방문일 +2일 기준으로 확진자 이동날짜에 이동한 회원의 이동 경로를 불러옴
    						List<Location> user_locList = locationRepository
    								.getLocationsByUserIdAndCreated_atEquals2(totalUser.get(j).getId(), covid.getCreated_at());
    						
    						// 만약 이동경로가 없으면 감염가능성 판단 안함
    						if (!user_locList.isEmpty()) {

    							// 회원의 이동경로따라 확진자와 겹치는 부분 확인
    							for (int k = 0; k < user_locList.size(); k++) {
    								
    								//확진자와 회원의 이동경로의 위도, 경도를 소수점 아래 5자리로 조정
    								double covidLatitude = Math.round(covid.getLatitude()*100000)/100000.0;
    								double covidLongtitude = Math.round(covid.getLongitude()*100000)/100000.0;
    								
    								
    								double userLatitude = Math.round(user_locList.get(k).getLatitude()*100000)/100000.0;
    								double userLongtitude = Math.round(user_locList.get(k).getLongitude()*100000)/100000.0;
    								
    								
    							
    								// 확진자 이동경로와 회원의 이동경로 사이 거리 측정
    								double dis = distance(covidLatitude, covidLongtitude,userLatitude, userLongtitude);
    								
    								// 이동경로가 100m 이내로 겹칠 경우
    								if (dis <= 100) { 
    									// 해당 유저를 경고메세지 전송 유저 목록에 추가
    									alertUser.add(user_locList.get(k).getUser().getId());
    									// 문자 발송 param : 회원 전화번호, 이동날짜, 주소
    									sendMessage(totalUser.get(j).getPhoneNum(), fm.format(user_locList.get(k).getCreated_at()),
    									getAddress(Double.toString(user_locList.get(k).getLatitude()),Double.toString(user_locList.get(k).getLongitude())).replace("\"", ""));
    									
    									//이미 경고메세지를 전송 했으므로  해당 유저에 대한 경로 탐색 종료
    									k = user_locList.size();
    								}
    							}
    						}
    					}
    				}
    			}

    }

    public void sendMessage(String phoneNum, String date, String address) {



		String api_key = "NCSCDR2E1ZHENOI2";
		String api_secret = "ZKPIREW75GAMJDKKNYMCMUTGIFCUMXCT";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", phoneNum);
		params.put("from", "01037256448");
		params.put("type", "SMS");
		params.put("text", date+", "+address+"에서 감염가능성이 확인되었습니다.");
		params.put("app_version", "나는여기"); // application name and version

		try {
			org.json.simple.JSONObject obj = (org.json.simple.JSONObject)coolsms.send(params);
			System.out.println(obj.toString());
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
	}

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

    	 double earthRadius = 6371000; //meters
		    double dLat = Math.toRadians(lon2-lon1);
		    double dLng = Math.toRadians(lat2-lat1);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(lon1)) * Math.cos(Math.toRadians(lon2)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    float dist = (float) (earthRadius * c);

		    return dist;
		
		
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public List<Covid> getCovidLocations(Timestamp date) {
        List<Covid> locations = covidRepository.getLocationsByCreated_atEquals(date);
        System.out.println(locations.toString());
        return locations;
    }
}

package com.example.demo.service;

import com.example.demo.domain.Location;
import com.example.demo.repository.LocationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CovidService {

    @Autowired
    LocationRepository locationRepository;

    public String data_api() throws Exception {
        String serviceKey="tufyZ6VLBILq5TuIblvCdAMD2fFepYFp29pal5%2BESdXrpluiCGBGv4ucJzMHTEKc%2FTQvdZDrlv1lILc8s8H88g%3D%3D";
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode("20200410", "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode("20200410", "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        JSONArray items=jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
//        System.out.println("Convert result : "+items.getString("item"));

        return items.toString();
    }

    public List<String> AlertCovid (String uid, Date date)
    {
        // -2~2일간 확진자 이동경로
        List<Location> covoidList=locationRepository.getLocationsByUserIdAndCreated_atBetween(uid,new Timestamp(date.getTime()));
        List<String> alertUser=new ArrayList<>();

        for(int i=0;i<covoidList.size();i++)
        {
            Location covid_loc=covoidList.get(i);
            List<Location> locations=locationRepository.getLocationsByCreated_atEqualsAndUserIdIsNot(covid_loc.getCreated_at(),uid);
            for(int j=0;j<locations.size();j++)
            {
                Location user_loc=locations.get(j);
                // 거리 비교 6피트 = 1.8288m
                double dis=distance(covid_loc.getLatitude(),covid_loc.getLongitude(),user_loc.getLatitude(),user_loc.getLongitude());
                if(dis<=1.8288) // 알림 발생
                    alertUser.add(user_loc.getUser().getId());
            }
        }

        return alertUser;
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
}

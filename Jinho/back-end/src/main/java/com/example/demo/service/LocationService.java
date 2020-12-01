package com.example.demo.service;

import com.example.demo.domain.Location;

import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class LocationService {
    @Value("${NaverID}")
    private String CLIENT_ID;

    @Value("${NaverSecret}")
    private String CLIENT_SECRET;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    public void setLocation(String uid,HashMap<String,String> map)
    {
        double latitude=Double.parseDouble(map.get("latitude"));
        double longitude=Double.parseDouble(map.get("longitude"));

        Location location=new Location();
//        location.setUser(userRepository.getOne(uid));
        location.setUser(userRepository.getOne(uid));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setCreated_at(new Timestamp(new Date().getTime()));

        locationRepository.save(location);
    }

    public List<Location> getLocations(String uid, Timestamp date)
    {
        List<Location> locations=locationRepository.getLocationsByUserIdAndCreated_atEquals(uid,date);

        System.out.println(locations.toString());
        return locations;
    }

    //directions 이용하기
    public List<Location> sendGet(String targetURL) throws Exception{

        URL url = new URL(targetURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET"); // optional default is GET
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID",CLIENT_ID);// 헤더 추가.
        con.setRequestProperty("X-NCP-APIGW-API-KEY",CLIENT_SECRET);

        // int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        br.close();

        System.out.println(response);

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response.toString());
        JSONObject parse_route = (JSONObject) obj.get("route");
        JSONArray parse_trafasts = (JSONArray) parse_route.get("trafast");
        JSONObject parse_trafast = (JSONObject) parse_trafasts.get(0);
        JSONArray parse_path = (JSONArray) parse_trafast.get("path");

        // path데이터-->array
        Object[] arr_path = parse_path.toArray();
        System.out.println(arr_path[0]);
        // 경도,위도값 path_list에 담아주기
        List<Location> locationList = new ArrayList<>();
        List<String> path_list = new ArrayList<>();
        for (int i = 0; i < arr_path.length; i++) {
            Location temp = new Location();
            String lat;
            String lng;
            String path[] = arr_path[i].toString().split(",");
            lat = path[1].substring(0, path[1].length()-1);
            lng = path[0].substring(1, path[0].length());
            temp.setLatitude(Double.parseDouble(lat));
            temp.setLongitude(Double.parseDouble(lng));
            locationList.add(temp);
        }
        return locationList;
    }
}

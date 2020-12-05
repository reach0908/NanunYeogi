package com.example.demo.service;

import com.example.demo.domain.Location;

//import com.example.demo.dto.Location_dto;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
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
    double d=1.0;
    String s=String.valueOf(d);
    public void setLocation(String uid,HashMap<String,String> map)
    {
        double latitude=Double.parseDouble(map.get("latitude"));
        double longitude=Double.parseDouble(map.get("longitude"));

        Location location=new Location();
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

    public List<Location> navigation(String uid, Timestamp date) throws Exception {
        List <Location> locations=locationRepository.getLocationsByUserIdAndCreated_atEquals(uid,date);

        if(locations.size()==0)
            return new ArrayList<>();
        Double lat_src=locations.get(0).getLatitude();
        Double lng_src=locations.get(0).getLongitude();
        String way_point="";

        for (int i=1;i<locations.size()-1;i++){
            Double lat = locations.get(i).getLatitude();
            Double lng = locations.get(i).getLongitude();
            way_point += lng.toString();
            way_point += ",";
            way_point += lat.toString();
            if(i!=locations.size()-2){
                way_point += ":";
            }
        }

        Double lat_dest = locations.get(locations.size()-1).getLatitude();
        Double lng_dest = locations.get(locations.size()-1).getLongitude();

        List<Location> path_arr=sendGet("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start="
                + lng_src + "," + lat_src + "&waypoints=" + way_point +"&goal=" + lng_dest + "," + lat_dest + "&option=trafast");

        return path_arr;
    }

    //directions 이용하기
    public List<Location> sendGet(String targetURL) throws Exception{

        URL url = new URL(targetURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET"); // optional default is GET
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID",CLIENT_ID);// 헤더 추가.
        con.setRequestProperty("X-NCP-APIGW-API-KEY",CLIENT_SECRET);

        // int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
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
            Location temp=new Location();

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

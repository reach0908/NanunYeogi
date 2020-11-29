package paastaproj.func1spring.service;

import paastaproj.func1spring.ApiKey;
import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.repositories.LocationRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class LocationService {

    private final LocationRepository locationRepository;
    //네이버지도 api key
    private final String CLIENT_ID = ApiKey.NaverID;
    private final String CLIENT_SECRET = ApiKey.NaverSecret;

    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    //위치저장
    public Long save(Location location){
        locationRepository.save(location);
        return location.getId();
    }

    //위치목록불러오기
    public List<Location> showAll(){
        return locationRepository.ShowAll();

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
        List<Location> locationList = new LinkedList<>();
        List<String> path_list = new LinkedList<>();
        for (int i = 0; i < arr_path.length; i++) {
            Location temp = new Location();
            String lat;
            String lng;
            String path[] = arr_path[i].toString().split(",");
            lat = path[1].substring(0, path[1].length()-1);
            lng = path[0].substring(1, path[0].length());
            temp.setLatitude(lat);
            temp.setLongitude(lng);
            locationList.add(temp);
        }
        return locationList;
    }
}

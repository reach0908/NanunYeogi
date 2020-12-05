package com.example.demo.controller;

import com.example.demo.domain.Location;
//import com.example.demo.dto.Location_dto;
import com.example.demo.domain.User;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.persistence.Column;
import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    // 위치 추가
    @PostMapping("/setlocations/{id}")
    public void StoreLocation(@PathVariable String id, @RequestBody HashMap<String,String> map)
    {
        System.out.println(id);
        System.out.println(map);
        locationService.setLocation(id,map);
    }

    // 이동경로
    @GetMapping(value = "/getlocations/{id}")
    public List<Location> GetLocations(@PathVariable String id, @RequestParam(name = "date") String temp) throws Exception
    {
//        String uid=webRequest.getAttribute("user_id", SCOPE_REQUEST).toString();
        Timestamp date=Timestamp.valueOf(temp);
        List<Location> locations=locationService.getLocations(id,date);
        System.out.println("getLocation : " + locations);
        return locations;
    }

    @GetMapping(value = "/getpaths/{id}")
    public List<HashMap<String,String>> GetPaths(@PathVariable String id, @RequestParam(name="date") String temp) throws Exception {
        Timestamp date = Timestamp.valueOf(temp);
        List<Location> marker = locationService.navigation(id,date);
        List<HashMap<String,String>> paths = new LinkedList<>();
        for(int i=0;i<marker.size();i++){
            Double lat = marker.get(i).getLatitude();
            Double lng = marker.get(i).getLongitude();
            HashMap<String,String> path = new HashMap<>();
            path.put("lat",lat.toString());
            path.put("lng", lng.toString());
            paths.add(path);

        }

        System.out.println("getPaths : " + paths);
        return paths;
    }



}

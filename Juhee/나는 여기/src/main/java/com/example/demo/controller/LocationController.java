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
    @PostMapping(value = "/locations")
    public void StoreLocation(NativeWebRequest webRequest,@RequestBody HashMap<String,String> map)
    {
        String uid=webRequest.getAttribute("user_id", SCOPE_REQUEST).toString();
        locationService.setLocation(uid,map);
    }

    // 이동경로
    @GetMapping(value = "/locations")
    public List<Location> GetLocations(NativeWebRequest webRequest,@RequestParam(name = "date") String temp)
    {
//        String uid=webRequest.getAttribute("user_id", SCOPE_REQUEST).toString();
        Timestamp date=Timestamp.valueOf(temp);
        System.out.println(date);
        List<Location> locations=locationService.getLocations("1549944379",date);

        return locations;
    }

    @GetMapping(value = "/locations/map")
    public List<Location> GetMaplocations(NativeWebRequest webRequest, @RequestParam(name = "date") String temp) throws Exception {
//        String uid=webRequest.getAttribute("user_id", SCOPE_REQUEST).toString();

        Timestamp date=Timestamp.valueOf(temp);
        List<Location> locations=locationService.navigation("1549944379",date);

        if(locations.size()==0)
            return new ArrayList<>();

        return locations;
    }



}

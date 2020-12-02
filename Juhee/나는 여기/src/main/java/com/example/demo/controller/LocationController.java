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

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    // 위치 추가
    @PostMapping(value = "/locations")
    public void StoreLocation(NativeWebRequest webRequest,@RequestBody HashMap<String,String> map)
    {
        User user=(User)webRequest.getAttribute("user", SCOPE_REQUEST);
        locationService.setLocation(user.getId(),map);
    }

    // 이동경로
    @GetMapping(value = "/locations")
    public List<Location> GetLocations(NativeWebRequest webRequest, @RequestBody HashMap<String,String> map)
    {
        User user=(User)webRequest.getAttribute("user", SCOPE_REQUEST);

        Timestamp date=Timestamp.valueOf(map.get("date"));
        System.out.println(map.get("date"));
        List<Location> locations=locationService.getLocations(user.getId(),date);

        return locations;
    }

    @GetMapping(value = "/locations/map")
    public List<Location> GetMaplocations(NativeWebRequest webRequest, @RequestBody HashMap<String,String> map) throws Exception {
        User user=(User)webRequest.getAttribute("user", SCOPE_REQUEST);

        Timestamp date=Timestamp.valueOf(map.get("date"));
        System.out.println(map.get("date"));
        List<Location> locations=locationService.navigation(user.getId(),date);

        return locations;
    }



}

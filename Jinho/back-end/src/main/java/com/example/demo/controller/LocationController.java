package com.example.demo.controller;

import com.example.demo.domain.Location;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    // 위치 추가
    @PostMapping(value = "/locations/{uid}")
    public void StoreLocation(@PathVariable String uid,@RequestBody HashMap<String,String> map)
    {
        locationService.setLocation(uid,map);
    }

    // 이동경로
    @GetMapping(value = "/locations/{uid}")
    public List<Location> GetLocations(@PathVariable String uid, @RequestBody HashMap<String,String> map)
    {
        Timestamp date=Timestamp.valueOf(map.get("date"));
        System.out.println(map.get("date"));
        List<Location> locations=locationService.getLocations(uid,date);

        return locations;
    }

    @GetMapping(value = "/locations/{uid}/map")
    public List<Location> GetMaplocations(@PathVariable String uid, @RequestBody HashMap<String,String> map)
    {
        Timestamp date=Timestamp.valueOf(map.get("date"));
        System.out.println(map.get("date"));
        List<Location> locations=locationService.getLocations(uid,date);

        return locations;
    }

}

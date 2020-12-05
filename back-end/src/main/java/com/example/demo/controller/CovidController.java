package com.example.demo.controller;

import com.example.demo.domain.Covid;
import com.example.demo.domain.Location;
import com.example.demo.domain.User;
import com.example.demo.service.CovidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@RestController
public class CovidController {

    @Autowired
    private CovidService covidService;

    @PostMapping(value = "/covid/{cid}")
    public void AlertCovid(@RequestParam int cid, @RequestBody HashMap<String,String> map)
    {
//        Date date=new Date(map.get("date"));
        // 비교후 메세지 전송
        covidService.AlertCovid(cid,map);
    }

    @GetMapping(value = "/covid")
    public Object data_api()
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String data=covidService.data_api();
            System.out.println("controller "+ data);
            System.out.println("controller "+objectMapper.convertValue(data,Object.class) );

            return objectMapper.convertValue(data,Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object();
    }

    @GetMapping("/getCovidLocations")
    public List<Covid> GetCovidLocations (@RequestParam(name = "date") String temp) throws Exception{
        Timestamp date=Timestamp.valueOf(temp);
        List<Covid> locations = covidService.getCovidLocations(date);
        System.out.println("covid locations : " +locations);
        return locations;
    }


}

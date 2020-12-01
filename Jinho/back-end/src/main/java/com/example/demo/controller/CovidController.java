package com.example.demo.controller;

import com.example.demo.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class CovidController {

    @Autowired
    private CovidService covidService;

    @RequestMapping(value = "/covid/{uid}",method = RequestMethod.GET)
    public void AlertCovid(@RequestParam String uid, @RequestBody HashMap<String,String> map)
    {
        Date date=new Date(map.get("date"));
        List<String> alertUser=covidService.AlertCovid(uid,date);
        // 알람 발생
    }


}

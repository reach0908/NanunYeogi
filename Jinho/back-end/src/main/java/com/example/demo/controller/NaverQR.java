package com.example.demo.controller;//package com.example.demo.controller;
//
//import com.example.demo.service.LocationService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.HashMap;
//
//@Controller
//public class NaverQR {
//
//    LocationService locationService;
//
//    @PostMapping(value = "/locations/{uid}")
//    public String naverQR(@PathVariable String uid, @RequestBody HashMap<String,String> map){
//
//        locationService.setLocation(uid,map);
//
//        return "https://nid.naver.com/login/privacyQR";
//    }
//
//}

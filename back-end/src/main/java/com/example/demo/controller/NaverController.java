//package com.example.demo.controller;
//
//import com.example.demo.service.NaverService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
////@RequestMapping("/")
//public class NaverController {
//
//    @Autowired
//    private NaverService naverService;
//
//    @GetMapping("naver")
//    public String naver()
//    {
//        return "naver";
//    }
//
//    @GetMapping("login")
//    public String callback()
//    {
//        String token="AAAAODoME_yp6-jsIAPBpRFZeWlUefs1Zt8zE1TlQwHtXx-0PfXKXAex14YzYw3jQp-qyM0iuVMuwf-z6WIYy0OzGlk";
//
//        return "navercallback";
//    }
//}

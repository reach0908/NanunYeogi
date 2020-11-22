package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class NaverController {

    @GetMapping("naver")
    public String naver()
    {
        return "naver";
    }

    @GetMapping("login")
    public String callback()
    {
        return "navercallback";
    }



}

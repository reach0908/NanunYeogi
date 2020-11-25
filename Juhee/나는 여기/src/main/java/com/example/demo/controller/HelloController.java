package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello()
    {
        return "hi";
    }

    @RequestMapping("/")
    public String Login()
    {
        return "Login";
    }


}
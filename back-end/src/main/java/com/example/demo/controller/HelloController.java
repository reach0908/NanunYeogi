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

    @GetMapping("/")
    public String Login()
    {
        return "Login";
    }


}
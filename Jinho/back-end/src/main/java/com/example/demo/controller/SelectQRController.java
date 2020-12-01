package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SelectQRController {

    //  QR코드 보여주기
    @RequestMapping(value = "/selectQR")
    public String SelectQR(){
        return "selectQR";
    }

}

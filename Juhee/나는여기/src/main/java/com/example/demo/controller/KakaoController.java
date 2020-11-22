package com.example.demo.controller;

import com.example.demo.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/kakao")
    public String kakao()
    {
        return "kakaocallback";
    }

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        return "kakaocallback";
    }

}

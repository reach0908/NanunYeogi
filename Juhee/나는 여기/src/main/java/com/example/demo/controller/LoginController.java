package com.example.demo.controller;

import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
//@RequestMapping("/")
public class LoginController {

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/kakao")
    public String kakao()
    {
        return "kakao";
    }

    @GetMapping("/kcallback")
    public String callback(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);


//        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
//        System.out.println("login Controller : " + userInfo);
//
//        if (userInfo.get("nickname") != null) {
//            session.setAttribute("userId", userInfo.get("nickname"));
//            session.setAttribute("access_Token", access_Token);
//        }

        return "kakaocallback";
    }


    @GetMapping("naver")
    public String naver()
    {
        return "naver";
    }

    @GetMapping("ncallback")
    public String callback()
    {
       // String token="AAAAODoME_yp6-jsIAPBpRFZeWlUefs1Zt8zE1TlQwHtXx-0PfXKXAex14YzYw3jQp-qyM0iuVMuwf-z6WIYy0OzGlk";

        return "navercallback";
    }
}

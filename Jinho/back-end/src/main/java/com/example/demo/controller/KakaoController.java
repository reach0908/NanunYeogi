//package com.example.demo.controller;
//
//import com.example.demo.service.KakaoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//
//@Controller
//public class KakaoController {
//
//    @Autowired
//    private KakaoService kakaoService;
//
//    @GetMapping("/kakao")
//    public String kakao()
//    {
//        return "kakao";
//    }
//
//     @RequestMapping("/callback")
//    public String callback(@RequestParam("code") String code, HttpSession session) {
//        String access_Token = kakaoService.getAccessToken(code);
//        System.out.println("controller access_token : " + access_Token);
//
//         HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
//         System.out.println("login Controller : " + userInfo);
//
//         if (userInfo.get("nickname") != null) {
//             session.setAttribute("userId", userInfo.get("nickname"));
//             session.setAttribute("access_Token", access_Token);
//         }
//
//        return "kakaocallback";
//    }
//
//}

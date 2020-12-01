package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import org.apache.catalina.filters.ExpiresFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
//@RequestMapping("/")
public class LoginController {

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/kakao")
    public String kakao()
    {
        return "kakao";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/kcallback")
    public String callback(@RequestParam("code") String code, HttpSession session) {

        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        if (userInfo.get("nickname") != null) {
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("access_Token", access_Token);
        }
        return "redirect:http://localhost:3000/qrcheckin";
    }

    // kakao 로그아웃 구현
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "redirect:http://localhost:3000";
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("naver")
    public String naver()
    {
        return "naver";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("ncallback")
    public String callback() throws ParseException
    {
        // String token="AAAAODoME_yp6-jsIAPBpRFZeWlUefs1Zt8zE1TlQwHtXx-0PfXKXAex14YzYw3jQp-qyM0iuVMuwf-z6WIYy0OzGlk";

        String token="AAAAOyO5Bqu8zoQAX3OiZZ9YR7UxgWWXgA2J7YbQpjKOHr8gofEsbEg3IvxhqMaQREAQ5f_USrDzm8ksAuPifjQNSns";
        String profile=naverService.getProfile(token);
        JSONParser parser=new JSONParser();
        Object obj = parser.parse( profile  );
        JSONObject jsonObj = (JSONObject) obj;

        String code = (String) jsonObj.get("response");
        System.out.println(profile);
        System.out.println("response"+code);

        return "navercallback";
    }
}

package com.example.demo.controller;

import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        if (userInfo.get("nickname") != null) {
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("access_Token", access_Token);
        }

        return "kakaocallback";
    }


    @GetMapping("naver")
    public String naver()
    {
        return "naver";
    }

    @GetMapping("ncallback")
    public String callback() throws ParseException {

        String token="AAAAOGlyXq5u6folkiTHTm_emqaKH15_KLYcJ7UiY-I7zedeMMMesewf750ZQjSH-6qJaB7GQwvJVKU_MgPhlb0Snjk";
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

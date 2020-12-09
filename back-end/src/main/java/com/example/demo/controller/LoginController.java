package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import com.example.demo.service.UserService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/")
public class LoginController {

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/kakao")
    public String kakao() {
        return "kakao";
    }

    @GetMapping("/kcallback")
    public RedirectView kcallback(@RequestParam("code") String code, HttpServletResponse res, HttpSession session) {
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        Map<String, Object> resultMap = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        String social = "kakao";

        String url = "http://nanunyeogi.paas-ta.org/qrcheckin?id=";

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        HttpStatus status = null;

        String id = userInfo.get("id").toString();
        String email = userInfo.get("email").toString();
        System.out.println(email);

        if (userInfo.get("userId") != null) {
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("access_Token", access_Token);
        }

        map.put("id", id);
        map.put("email", email);
        map.put("social", social);

        User user = userService.signin(map);
        if (user.getPhoneNum() == null) {
            String registerUrl = "http://nanunyeogi.paas-ta.org/phoneregister?id=";
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(registerUrl + id);
            return redirectView;
        }

        status = HttpStatus.ACCEPTED;

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url + id);

        return redirectView;

    }

    @RequestMapping(value = "/logout")
    public RedirectView logout(HttpSession session) {
        String url = "http://nanunyeogi.paas-ta.org";
        kakaoService.kakaoLogout((String) session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("naver")
    public String naver() {
        return "naver";
    }

    @GetMapping("/ncallback")
    public RedirectView ncallback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse res) throws ParseException, UnsupportedEncodingException {

        String social = "naver";
        String url = "http://nanunyeogi.paas-ta.org/qrcheckin?id=";
        RedirectView redirectView = new RedirectView();
        HashMap<String, Object> token_map = naverService.getToken(code, state);

//            if(token_map.get("status").equals(true))
//            {
        String response = token_map.get("res").toString();
        System.out.println("naver response" + response);

        JSONParser parser = new JSONParser();
        JSONObject obj_token = (JSONObject) parser.parse(response);
        String token = obj_token.get("access_token").toString();
        System.out.println("naver token" + token);

        String profile = naverService.getProfile(token);

        JsonParser jsonparser = new JsonParser();
        JsonElement element = jsonparser.parse(profile);

        JsonObject res_profile = element.getAsJsonObject().get("response").getAsJsonObject();

        String id = res_profile.getAsJsonObject().get("id").getAsString();
        String email = res_profile.getAsJsonObject().get("email").getAsString();

        HashMap<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("email", email);
        map.put("social", social);
        System.out.println(profile);

        User user = userService.signin(map);
        if (user.getPhoneNum() == null) {
            String registerUrl = "http://nanunyeogi.paas-ta.org/phoneregister?id=";
            RedirectView redirectView2 = new RedirectView();
            redirectView2.setUrl(registerUrl + id);
            return redirectView2;
        }
//
//            String jwt_token=jwtService.create(user);
//            res.setHeader("jwt-auth-token",jwt_token);

        redirectView.setUrl(url + id);
        return redirectView;

    }
}

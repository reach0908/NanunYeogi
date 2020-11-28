package com.example.demo;

import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @Test
    void contextLoads() throws ParseException {

        String token="W9myP6D6vrAnxyyM0L4-WdxQpp1g6MO-7LB-AworDR8AAAF2D5o17A";
        HashMap<String,Object> Info=kakaoService.getUserInfo(token);
        if(Info.isEmpty())
        {
           // status= HttpStatus.UNAUTHORIZED;
           // return new ResponseEntity<Map<String,Object>>(resultMap,status);
        }
//        String id=Info.get("id").toString();
//        String email=Info.get("email").toString();
        System.out.println(Info.get("nickname"));
//        map.put("id",id);
//        map.put("email",email);
//        String token="AAAAOGlyXq5u6folkiTHTm_emqaKH15_KLYcJ7UiY-I7zedeMMMesewf750ZQjSH-6qJaB7GQwvJVKU_MgPhlb0Snjk";
//        String profile=naverService.getProfile(token);
//        JSONParser parser=new JSONParser();
//        Object obj = parser.parse(profile);
//        JSONObject jsonObj = (JSONObject) obj;
//
//        obj=parser.parse(jsonObj.get("response").toString());
//        jsonObj = (JSONObject) obj;
//
//        String id = jsonObj.get("id").toString();
//        System.out.println(profile);
//        System.out.println("id"+id);
    }

}

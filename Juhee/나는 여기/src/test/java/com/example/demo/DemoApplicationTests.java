package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {


    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    @Test
    void contextLoads() throws Exception {

        String jwt="eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiLroZzqt7jsnbjthqDtgbAiLCJleHAiOjE2MDY5Mjg0MzQsImlhdCI6MTYwNjkyMjQzNCwidXNlciI6eyJpZCI6IjE1Mzk4MzMxNzYiLCJlbWFpbCI6Imtha2FvX2VtYWlsIiwic29jaWFsIjoia2FrYW8iLCJwaG9uZSI6bnVsbH19.pXJ4y-yqrzUMJxCXHcbVT-q-g8Iix4nC51zWOQbiWDw";
        Object obj= Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        System.out.println(obj.toString());

        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Map<String, Object>> map = oMapper.convertValue(obj, Map.class);
        System.out.println(map.get("body").get("user"));
        User user=oMapper.convertValue(map.get("body").get("user"),User.class);
        System.out.println(user.getId());
    }

}

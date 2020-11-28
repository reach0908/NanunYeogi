package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @CrossOrigin("http://localhost:8081")
    @GetMapping(value = "login")
    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> signin(@RequestBody HashMap<String ,String > map, HttpServletResponse res)
    {
        System.out.println("LOGIN");
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        ObjectMapper objectMapper=new ObjectMapper();

        // 토큰 확인
        String token=map.get("token");
        String social=map.get("social");
        if(social=="naver")
        {
            String profile=naverService.getProfile(token);
            if(profile==null)
            {
                status=HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<Map<String,Object>>(resultMap,status);
            }
            System.out.println(profile);
        }
        else if(social=="kakao")
        {
            HashMap<String,Object> Info=kakaoService.getUserInfo(token);
            if(Info.isEmpty())
            {
                status=HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<Map<String,Object>>(resultMap,status);
            }
        }


        try{
            User loginUser=userService.signin(map);
           // Map mapper=objectMapper.convertValue(loginUser,Map.class);

            String jwt_token=jwtService.create(loginUser);
            res.setHeader("jwt-auth-token",jwt_token);

            resultMap.put("status",true);
            resultMap.put("user",loginUser);
            resultMap.put("locations",locationService.getLocations(loginUser.getId(),new Timestamp(new Date().getTime())));

            status=HttpStatus.ACCEPTED;
            return new ResponseEntity<Map<String,Object>>(resultMap,status);


//            if(loginUser==null)
//            {
//                status=HttpStatus.UNAUTHORIZED;
//                return new ResponseEntity<Map<String,Object>>(resultMap,status);
//            }

        }catch (Exception e){
//            System.out.println("로그인 실패 "+e);
//            resultMap.put("error","No user");
            resultMap.put("message",e.getMessage());
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String,Object>>(resultMap,status);
    }
}

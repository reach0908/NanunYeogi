//package com.example.demo.controller;
//
//import com.example.demo.domain.User;
//import com.example.demo.service.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.view.RedirectView;
//
//import javax.servlet.http.HttpServletResponse;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class JwtController {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private LocationService locationService;
//
//    @Autowired
//    private NaverService naverService;
//
//    @Autowired
//    private KakaoService kakaoService;
////
////    @CrossOrigin("http://localhost:8081")
//    @GetMapping(value = "login")
//    @ExceptionHandler
//    public ResponseEntity<Map<String,Object>> signin(@RequestBody HashMap<String ,String > map, HttpServletResponse res){
//        System.out.println("LOGIN");
//        Map<String,Object> resultMap=new HashMap<>();
//        HttpStatus status=null;
//        ObjectMapper objectMapper=new ObjectMapper();
//
//
//        String token=map.get("token");
//        String social=map.get("social");
//        // 토큰 확인
//        if(social.equals("naver"))
//        {
//            String profile=naverService.getProfile(token);
//            if(profile.isEmpty())
//            {
//                status=HttpStatus.UNAUTHORIZED;
//                return new ResponseEntity<Map<String,Object>>(resultMap,status);
//            }
//            JsonParser parser=new JsonParser();
//            JsonElement element = parser.parse(profile);
//
//            JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
//
//            String id=response.getAsJsonObject().get("id").getAsString();
//            String email=response.getAsJsonObject().get("email").getAsString();
//
//            System.out.println(profile);
//            map.put("id",id);
//            map.put("email",email);
//        }
//        else
//        {
//            HashMap<String,Object> Info=kakaoService.getUserInfo(token);
//            if(Info.isEmpty())
//            {
//                status=HttpStatus.UNAUTHORIZED;
//                return new ResponseEntity<Map<String,Object>>(resultMap,status);
//            }
//            String id=Info.get("id").toString();
//            // email확인x
//            String email=Info.get("nickname").toString();
//            System.out.println(email);
//            map.put("id",id);
//            map.put("email","kakao_email");
//        }
//
//
//        try{
//            User loginUser=userService.signin(map);
//           // Map mapper=objectMapper.convertValue(loginUser,Map.class);
//
//            String jwt_token=jwtService.create(loginUser);
//            res.setHeader("jwt-auth-token",jwt_token);
//
//            resultMap.put("status",true);
//            resultMap.put("user",loginUser);
//
//            RedirectView redirectView = new RedirectView();
//            redirectView.setUrl("http://localhost:3000/qrcheckin");
//
////            return "redirect:http://localhost:3000/qrcheckin/" + access_Token;
//
//            //resultMap.put("locations",locationService.getLocations(loginUser.getId(),new Timestamp(new Date().getTime())));
//
////            status=HttpStatus.ACCEPTED;
////            return new ResponseEntity<Map<String,Object>>(resultMap,status);
//
//
////            if(loginUser==null)
////            {
////                status=HttpStatus.UNAUTHORIZED;
////                return new ResponseEntity<Map<String,Object>>(resultMap,status);
////            }
//
//        }catch (Exception e){
////            System.out.println("로그인 실패 "+e);
////            resultMap.put("error","No user");
//            resultMap.put("message",e.getMessage());
//            status=HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String,Object>>(resultMap,status);
//    }
//}

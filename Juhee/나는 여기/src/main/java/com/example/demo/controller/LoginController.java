package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.KakaoService;
import com.example.demo.service.NaverService;
import com.example.demo.service.UserService;
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
    public String kakao()
    {
        return "kakao";
    }

    @GetMapping("/kcallback")
    public RedirectView kcallback(@RequestParam("code") String code, HttpServletResponse res) {
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        Map<String,Object> resultMap=new HashMap<>();
        HashMap<String ,String > map=new HashMap<>();
        String social="kakao";

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        HttpStatus status=null;

//        if(userInfo.isEmpty())
//        {
//            status=HttpStatus.UNAUTHORIZED;
//            //return new ResponseEntity<Map<String,Object>>(resultMap,status);
//        }
        String id=userInfo.get("id").toString();
        String email=userInfo.get("nickname").toString();
        System.out.println(email);

        map.put("id",id);
        map.put("email","kakao_email");
        map.put("social",social);

        User user=userService.setUser(map);
        String jwt_token=jwtService.create(user);
        res.setHeader("jwt-auth-token",jwt_token);

        resultMap.put("status",true);
        resultMap.put("user",user);
        //resultMap.put("locations",locationService.getLocations(loginUser.getId(),new Timestamp(new Date().getTime())));

        status=HttpStatus.ACCEPTED;
//        return new ResponseEntity<Map<String,Object>>(resultMap,status);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:3000/qrcheckin");
//        redirectView.set

        return redirectView;
//        return "redirect:http://localhost:3000/qrcheckin/" + access_Token;
    }


    @GetMapping("naver")
    public String naver()
    {
        return "naver";
    }

    @GetMapping("ncallback")
    public String ncallback(@RequestParam("code") String code,@RequestParam("code") String state,HttpServletResponse res) throws ParseException {

        //String token="AAAAOGlyXq5u6folkiTHTm_emqaKH15_KLYcJ7UiY-I7zedeMMMesewf750ZQjSH-6qJaB7GQwvJVKU_MgPhlb0Snjk";

        try {
            HashMap<String,Object> map=naverService.getToken(code,state);

            if(map.get("status").equals(true))
            {
                String response=map.get("res").toString();
                System.out.println("naver response"+response);
                JSONParser parser=new JSONParser();
                JSONObject obj_token=(JSONObject)parser.parse(response);
                String token=obj_token.get("access_token").toString();
                System.out.println("naver token"+token);

                String profile=naverService.getProfile(token);

                Object obj = parser.parse( profile  );
                JSONObject jsonObj = (JSONObject) obj;
                System.out.println(profile);
                
//                String code = (String) jsonObj.get("response");
                
//                System.out.println("response"+code);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "navercallback";
    }
}

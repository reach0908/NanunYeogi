package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.CovidService;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Controller
public class CovidController {

    @Autowired
    private CovidService covidService;

    @RequestMapping(value = "/covid",method = RequestMethod.GET)
    public void AlertCovid(NativeWebRequest webRequest, @RequestBody HashMap<String,String> map)
    {

        User user=(User)webRequest.getAttribute("user", SCOPE_REQUEST);
        Date date=new Date(map.get("date"));
        List<String> alertUser=covidService.AlertCovid(user.getId(),date);
        // 알람 발생

//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("http://www.naver.com");


    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/covid")
    public ResponseEntity<Map<String,Object>> data_api(HttpServletResponse res)
    {
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;

        try {
            String data=covidService.data_api();

//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse( data );
//            JSONObject jsonObj = (JSONObject) obj;
            status=HttpStatus.ACCEPTED;
            resultMap.put("items",data);
            resultMap.put("status",true);

            return new ResponseEntity<Map<String,Object>>(resultMap,status);
        } catch (Exception e) {
            e.printStackTrace();
        }

        status=HttpStatus.BAD_REQUEST;
        return new ResponseEntity<Map<String,Object>>(resultMap,status);
    }


}

package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping(value = "/users")
//    public void StoreUser(@RequestBody HashMap<String,String> map)
//    {
//        String name=map.get("username");
//        userService.setUser(name);
//    }

    @GetMapping("/users")
    public List<User> getallUsers(NativeWebRequest webRequest) {
        String uid = webRequest.getAttribute("user_id", SCOPE_REQUEST).toString();

        System.out.println("getUserId " + uid);
        List<User> users = userService.AllUsers();
        return users;
    }

    @PostMapping("/phoneregister/{id}")
    public void updatePhone(@PathVariable String id, @RequestBody HashMap<String, String> map) {
        String Phone = map.get("phoneNumber");

        System.out.println("user id : " + id);
        System.out.println("phoneNumber : " + Phone);

        User user = userService.updatePhone(id, Phone);
    }


//    @GetMapping("/user")
//    public void setUser()
//    {
//        HashMap<String,String> map=new HashMap<>();
//
//
//        map.put("id","1");
//        map.put("email","1@1.1");
//        map.put("social","kakao");
//        userService.setUser(map);
//
//        //List <User> users=userService.AllUsers();
//        //return users;
//    }

}

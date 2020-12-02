package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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
    public List<User> getallUsers(NativeWebRequest webRequest)
    {
        User user=(User)webRequest.getAttribute("user", SCOPE_REQUEST);

        System.out.println("getUser "+user);
        List <User> users=userService.AllUsers();
        return users;
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

package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

}

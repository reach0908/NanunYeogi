package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User setUser(HashMap<String,String> map)
    {
        User user=new User();

        user.setId(map.get("id"));
        user.setEmail(map.get("email"));
        user.setSocial(map.get("social"));

        userRepository.save(user);

        return user;
    }

    public User signin(HashMap<String,String> map)
    {
        User user=userRepository.getUserById(map.get("id"));
//        System.out.println("user"+user.toString());

        if(user==null)
        {
            user=userRepository.getUserByEmail(map.get("email"));
            if(user==null)
                user=setUser(map);
        }

        return user;
    }
}

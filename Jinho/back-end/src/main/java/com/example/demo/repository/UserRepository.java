package com.example.demo.repository;


import com.example.demo.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public User getUserByEmail(String email);

    public User getUserById(String id);
    
    @Query(value = "select * from user " 
            ,nativeQuery = true)
public List<User> getTotalUser();
}

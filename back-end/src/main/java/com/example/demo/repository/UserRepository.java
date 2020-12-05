package com.example.demo.repository;


import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public User getUserByEmail(String email);

    public User getUserById(String id);

//    public List<User> getUsers();
//    public boolean findByIdAndEmailIsNull(String uid);

}

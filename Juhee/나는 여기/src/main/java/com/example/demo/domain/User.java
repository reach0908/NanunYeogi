package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private int id;

    @Column(nullable = false,unique = true)
    private String username;

//    @Column
//    private boolean is_kakao=false;
//
//    @Column
//    private boolean is_naver=false;
}

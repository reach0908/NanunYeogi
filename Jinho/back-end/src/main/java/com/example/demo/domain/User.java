package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
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

    @Id
    @Column(updatable=false)
    private String id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String social;

//    User(String id, String email){
//        this.id = id;
//        this.email = email;
//    }


//    @OneToMany(mappedBy = "location")
//    private List<Location> locations=new ArrayList<>();

//    @Column
//    private boolean is_kakao=false;
//
//    @Column
//    private boolean is_naver=false;
}

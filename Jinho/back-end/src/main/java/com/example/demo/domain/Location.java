package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 위도
    @Column
    private double latitude;

    // 경도
    @Column
    private double longitude;

    @Column
    private Timestamp created_at;



}

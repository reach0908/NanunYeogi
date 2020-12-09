package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@Entity
@Table
//@IdClass(CovidPk.class)
public class Covid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private int id;

    @Column
    private int covid_id;

    @Column
    private double latitude;

    // 경도
    @Column
    private double longitude;

    @Column
    private Timestamp created_at;
}


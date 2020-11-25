package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table
public class Covid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Date created_at;
}

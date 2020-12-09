package com.example.demo.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import lombok.Data;
@Data
public class CovidPk implements Serializable {
	 private Timestamp created_at;
	 private int covid_id;
	 private double longitude;
	 private double latitude;

	
	    
}

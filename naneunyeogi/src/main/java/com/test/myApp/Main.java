package com.test.myApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.test.mapper.CovidAlertMapper;
import com.test.service.CovidAlertServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;
@Controller
public class Main {
	
	
	public static void main(String[] args) {
		InternalMethods ims = new InternalMethods(null);
		ims.covidCloserCheck(1, 1, 1);
		System.out.println("closerCheckEnd");
	}

}

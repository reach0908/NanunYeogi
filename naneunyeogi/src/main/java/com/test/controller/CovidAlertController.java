package com.test.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.domain.Naneun_userVO;
import com.test.service.CovidAlertService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/covidAlert/*")
@AllArgsConstructor
public class CovidAlertController {
	
	//DI
	private CovidAlertService service;
	//CovidCloserCheck
	//크롤링중 새로운 확진자가 발생할 경우
	//확진자가 다녀간 위치에 고유 확진자 번호(covidNum)와 이동경로에 따른 order(covidOrder) 부여
	//해당 위치를 User 동선과 겹치는 확인
	
	@GetMapping("/covidCloserCheck")
	public void covidCloserCheck( int userNum,int covidNum, int covidOrder, Model model
		//로그인 페이지 구현시 로그인 정보를 받아옴
//			@ModelAttribute Naneun_userVO userVO,HttpSession session,
//			,RedirectAttributes rttr
			) {
		
//		int userOrderCnt = service.getUserOrderCnt(userVO,session);
//		int[] userOrder=service.getUserOrdersO(userVO,session);
		boolean covidAlert =false;
		int[] userOrders=service.getUserOrders(userNum);
		HashMap<String, Integer> covid = new HashMap<>();
		covid.put("covidNum", covidNum);
		covid.put("covidOrder", covidOrder);
		double covidLongtitude=service.getCovidLongtitude(covid);
		double covidLatitude=service.getCovidLatitude(covid);
		
		log.info("covidCloserCheck");
		for(int i =0; i<userOrders.length;i++) {
			int userOrder = userOrders[i];
			HashMap<String,Integer> user = new HashMap<>();
			user.put("userNum", userNum);
			user.put("userOrder", userOrder);
			double userLongtitude = service.getUserLongtitude(user);
			double userLatitude = service.getUserLatitude(user);
			
		}
		
		
		
	}
	
	
	
}




















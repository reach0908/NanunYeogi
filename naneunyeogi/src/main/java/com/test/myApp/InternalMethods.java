package com.test.myApp;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.controller.CovidAlertController;
import com.test.domain.Covid_dangerVO;
import com.test.service.CovidAlertService;
import com.test.service.CovidAlertServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@AllArgsConstructor
public class InternalMethods {
	
	private CovidAlertService service;
	

	

	public void covidCloserCheck(int userNum, int covidNum, int covidOrder
	// 로그인 페이지 구현시 로그인 정보를 받아옴
//				@ModelAttribute Naneun_userVO userVO,HttpSession session,
//				,RedirectAttributes rttr
	) {

//			int userOrderCnt = service.getUserOrderCnt(userVO,session);
//			int[] userOrder=service.getUserOrdersO(userVO,session);
		int userCnt = service.getUserOrderCnt(userNum);
		int[] userOrders = new int[userCnt];
				service.getUserOrders(userNum);
		HashMap<String, Integer> covid = new HashMap<>();
		covid.put("covidNum", covidNum);
		covid.put("covidOrder", covidOrder);
		double covidLongtitude = service.getCovidLongtitude(covid);
		double covidLatitude = service.getCovidLatitude(covid);

		for (int i = 0; i < userCnt; i++) {
			int userOrder = userOrders[i];
			HashMap<String, Integer> user = new HashMap<>();
			user.put("userNum", userNum);
			user.put("userOrder", userOrder);
			double userLongtitude = service.getUserLongtitude(user);
			double userLatitude = service.getUserLatitude(user);
			//covid user 간 거리
			double CUdistance = distance(covidLatitude,covidLongtitude,userLatitude,userLongtitude,"meter");
			if(CUdistance<=100) {
				Covid_dangerVO covidDangerVO = new Covid_dangerVO();
				covidDangerVO.setCovid_num(covidNum);
				covidDangerVO.setCovid_order(covidOrder);
				covidDangerVO.setUser_num(userNum);
				covidDangerVO.setUser_order(userOrder);
				service.insertCovidDangerInfo(covidDangerVO);
			}
		}

	}

	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		if (unit == "kilometer") {
			dist = dist * 1.609344;
		} else if (unit == "meter") {
			dist = dist * 1609.344;
		}

		return (dist);
	}

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);

	}

}

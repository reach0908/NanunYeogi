package com.test.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.test.domain.Covid_dangerVO;
import com.test.domain.Naneun_userVO;


public interface CovidAlertService {
	//메소드 설계시 메소드 이름은 현실적인 로직의 이름을 붙여준다.
		public double getCovidLongtitude(HashMap<String, Integer> covid);
		public double getCovidLatitude(HashMap<String, Integer> covid);
		public double getUserLongtitude(HashMap<String, Integer> user);
		public double getUserLatitude(HashMap<String, Integer> user);
		public int getUserorder(int userNum);
		public int getCovidorder(int covidNum);
		public int[] getUserOrders(int userNum);
		public int getUserOrderCnt(int userNum);
		public void insertCovidDangerInfo(Covid_dangerVO covidDangerVO);
		public void covidCloserCheck(int userNum, int covidNum, int covidOrder
				// 로그인 페이지 구현시 로그인 정보를 받아옴
//							@ModelAttribute Naneun_userVO userVO,HttpSession session,
//							,RedirectAttributes rttr
				);
//		public int[] getUserOrdersO(Naneun_userVO userVO, HttpSession session);
//		public int getUserOrderCnt(Naneun_userVO userVO, HttpSession session);
		
	}






package com.test.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.test.domain.Covid_dangerVO;
import com.test.mapper.CovidAlertMapper;
import com.test.myApp.InternalMethods;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
@Service
@Log4j
@AllArgsConstructor

public class CovidAlertServiceImpl implements CovidAlertService {
//	@Setter(onMethod_=@Autowired)
	private CovidAlertMapper mapper;
	
	
	@Override
	public double getCovidLongtitude(HashMap<String, Integer> covid) {
		log.info("get Covid Longtitude");
		return mapper.getCovidLongtitude(covid);
	}

	@Override
	public double getCovidLatitude(HashMap<String, Integer> covid) {
		log.info("get Covid Latitude");
		return mapper.getCovidLatitude(covid);
	}

	@Override
	public double getUserLongtitude(HashMap<String, Integer> user) {
		log.info("get User Longtitude");
		return mapper.getUserLongtitude(user);
	}

	@Override
	public double getUserLatitude(HashMap<String, Integer> user) {
		log.info("get User Latitude");
		return mapper.getUserLatitude(user);
	}

	@Override
	public int getUserorder(int userNum) {
		log.info("get User Longtitude");
		return mapper.getUserorder(userNum);
	}

	@Override
	public int getCovidorder(int covidNum) {
		log.info("get User Longtitude");
		return mapper.getCovidorder(covidNum);
	}

	@Override
	public int[] getUserOrders(int userNum) {
		System.out.println("userNum들어옴");
		log.info("get user Orders");
		return mapper.getUserOrders(userNum);
	}

	@Override
	public int getUserOrderCnt(int userNum) {
		log.info("get UserOrder Count");
		return mapper.getUserOrderCnt(userNum);
	}

	@Override
	public void insertCovidDangerInfo(Covid_dangerVO covidDangerVO) {
		log.info("insert covidDangerInfo");
		mapper.insertCovidDangerInfo(covidDangerVO);
		
	}
	
	@Override
	public void covidCloserCheck(int userNum, int covidNum, int covidOrder
			// 로그인 페이지 구현시 로그인 정보를 받아옴
//						@ModelAttribute Naneun_userVO userVO,HttpSession session,
//						,RedirectAttributes rttr
			) {

//					int userOrderCnt = service.getUserOrderCnt(userVO,session);
//					int[] userOrder=service.getUserOrdersO(userVO,session);
		log.info("감염위험성 판단 시작");
				int userCnt = getUserOrderCnt(userNum);
				log.info("오더 수 구함");
				int[] userOrders = new int[userCnt];
						userOrders=getUserOrders(userNum);
						log.info("오더 구함");
				HashMap<String, Integer> covid = new HashMap<>();
				covid.put("covidNum", covidNum);
				covid.put("covidOrder", covidOrder);
				double covidLongtitude = Math.round(getCovidLongtitude(covid)*100000)/100000.0;
				double covidLatitude = Math.round(getCovidLatitude(covid)*100000)/100000.0;
				log.info("경도 : "+covidLongtitude);
				for (int i = 0; i < userCnt; i++) {
					int userOrder = userOrders[i];
					HashMap<String, Integer> user = new HashMap<>();
					user.put("userNum", userNum);
					user.put("userOrder", userOrder);
					double userLongtitude = Math.round(getUserLongtitude(user)*100000)/100000.0;
					double userLatitude = Math.round(getUserLatitude(user)*100000)/100000.0;
					log.info("유저 경도 : " + userLongtitude);
					//covid user 간 거리
					double CUdistance = distance(covidLongtitude,covidLatitude,userLongtitude,userLatitude,"meter");
					log.info("거리 = "+CUdistance);
					if(CUdistance<=100&&CUdistance>=-100) {
						Covid_dangerVO covidDangerVO = new Covid_dangerVO();
						covidDangerVO.setCovid_num(covidNum);
						covidDangerVO.setCovid_order(covidOrder);
						covidDangerVO.setUser_num(userNum);
						covidDangerVO.setUser_order(userOrder);
						insertCovidDangerInfo(covidDangerVO);
						log.info("Covid Danger");
						log.info("order"+i+"danger");
					}
				}
				log.info("closerCheck success");
			}

			public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
//					
				log.info("거리 구하러 왔어요");
				log.info("확진자 경도 : "+lat1+"확진자 위도 : "+lon1+ "유저 경도 : "+lat2+"유저 위도 : "+lon2);
				
				 double earthRadius = 6371000; //meters
				    double dLat = Math.toRadians(lat2-lat1);
				    double dLng = Math.toRadians(lon2-lon1);
				    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				               Math.sin(dLng/2) * Math.sin(dLng/2);
				    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
				    float dist = (float) (earthRadius * c);

				    return dist;
				
				
				
				
				
				
				
//					log.info(unit);
//				double theta = lon1 - lon2;
//				double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
//						+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//
//				dist = Math.acos(dist);
//				dist = rad2deg(dist);
//				dist = dist * 60 * 1.1515;
//
//				if (unit == "kilometer") {
//					dist = dist * 1.609344;
//				} else if (unit == "meter") {
//					dist = dist * 1609.344;
//				}
//
//				return (dist);
			}

			// This function converts decimal degrees to radians
			public double deg2rad(double deg) {
				return (deg * Math.PI / 180.0);
			}

			// This function converts radians to decimal degrees
			public double rad2deg(double rad) {
				return (rad * 180 / Math.PI);

			}

	
	
	
	

}

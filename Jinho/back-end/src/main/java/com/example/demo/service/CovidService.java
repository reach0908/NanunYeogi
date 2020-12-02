package com.example.demo.service;

import com.example.demo.domain.Covid;
import com.example.demo.domain.Location;
import com.example.demo.domain.User;
import com.example.demo.repository.CovidRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

@Service
public class CovidService {

	@Autowired
	LocationRepository locationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CovidRepository covidRepository;

	public List<String> AlertCovid(int cid, HashMap<String, String> map) {

		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = fm.parse(map.get("Date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// -2~2일간 확진자 이동경로
		List<Covid> covoidList = covidRepository.getCovidByCovidIdAndCreated_atBetween(cid,
				new Timestamp(date.getTime()));

		// 테스트 용 로그
		// for(int i=0; i<covoidList.size();i++) {
//		System.out.println(covoidList.get(i));
//	}
		List<String> alertUser = new ArrayList<>();
		List<User> totalUser = new ArrayList<>();
		// 모든 회원 목록
		totalUser = userRepository.getTotalUser();

		boolean checked = false;
		for (int i = 0; i < covoidList.size(); i++) {
			Covid covid = covoidList.get(i);

			// 테스트 용 로그
//		System.out.println("확진자 하나 가져옴");
//		System.out.println(covid);

			for (int j = 0; j < totalUser.size(); j++) {
				checked = false;
				for (int l = 0; l < alertUser.size(); l++) {
					if (alertUser.get(l) == totalUser.get(j).getId()) {
						checked = true;
					}
				}
				if (!checked) {
					// 모든 회원 목록에서 하나를 가져와확진자 이동날짜에 이동한 회원의 이동 경로
					List<Location> user_locList = locationRepository
							.getLocationsByUserIdAndCreated_atEquals(totalUser.get(j).getId(), covid.getCreated_at());
//					

					// 테스트 용 로그
//				for(int k =0; k<user_locList.size();k++) {
//						System.out.println("유저 정보");
//						System.out.println(user_locList.get(k));
//						System.out.println(covid);
//					}

					// 해당 하는 유저 목록 없으면 실행 안함
					if (!user_locList.isEmpty()) {

						// 유저 이동경로따라 확진자와 겹치는 부분 확인
						for (int k = 0; k < user_locList.size(); k++) {
							// 거리 비교 6피트 = 1.8288m
//						System.out.println("가능성 체크 시작");

							double covidLatitude = Math.round(covid.getLatitude() * 100000) / 100000.0;
							double covidLongtitude = Math.round(covid.getLongitude() * 100000) / 100000.0;

							double userLatitude = Math.round(user_locList.get(k).getLatitude() * 100000) / 100000.0;
							double userLongtitude = Math.round(user_locList.get(k).getLongitude() * 100000) / 100000.0;

							// 테스트용 로그

							/*
							 * System.out.println(covid.getCovid_id()); System.out.println(covidLatitude);
							 * System.out.println(covidLongtitude); System.out.println(userLatitude);
							 * System.out.println(userLongtitude);
							 */

							double dis = distance(covidLatitude, covidLongtitude, userLatitude, userLongtitude);

							System.out.println("거리:" + dis);
							if (dis <= 100) { // 겹치는 부분이 있을 경우
								alertUser.add(user_locList.get(k).getUser().getId());// 메세지 보낸 유저목록에 포함
								// 문자 발송
								sendMessage(totalUser.get(j).getPhoneNum(),
										user_locList.get(k).getCreated_at().toString(),
										getAddress(Double.toString(user_locList.get(k).getLatitude()),
												Double.toString(user_locList.get(k).getLongitude())));
								// TEST console 출력
								/*
								 * System.out.println(totalUser.get(j).getPhoneNum() + "(으)로 전송\n" +
								 * user_locList.get(k).getCreated_at().toString() + " " +
								 * getAddress(Double.toString(user_locList.get(k).getLatitude()),
								 * Double.toString(user_locList.get(k).getLongitude())) +
								 * "에서 감염가능성이 확인 되었습니다.");
								 */

								k = user_locList.size();// 해당 유저에 대한 경로 탐색 종료
							}
						}
					}
				}
			}
		}
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {

		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lon2 - lon1);
		double dLng = Math.toRadians(lat2 - lat1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lon1))
				* Math.cos(Math.toRadians(lon2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public void sendMessage(String phoneNum, String date, String address) {

		/**
		 * @class ExampleSend
		 * @brief This sample code demonstrate how to send sms through CoolSMS Rest API
		 *        PHP
		 */

		String api_key = "NCSCDR2E1ZHENOI2";
		String api_secret = "ZKPIREW75GAMJDKKNYMCMUTGIFCUMXCT";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", "01037256448");
		params.put("from", "01037256448");
		params.put("type", "SMS");
		params.put("text", "2020-07-08, 서울시 중랑구 상도동 상도로68길 12에서 감염가능성이 확인되었습니다. http://localhost:8080/kakao에서 확인하세요");
		params.put("app_version", "나는여기"); // application name and version

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
	}
//?????????????????????????????????????????????????????????
//	public User getUser(String uid) {
//		String phoneNum;
//		User user = new User();
//		return user = userRepository.getUserById(uid);
//
//	}

	public String getAddress(String lat, String lon) {

		try {
			BufferedReader in = null;
			JsonParser parser = new JsonParser();

			URL urlObject = new URL("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat
					+ "&longitude=" + lon + "&localityLanguage=ko");
			HttpsURLConnection con = (HttpsURLConnection) urlObject.openConnection();
			con.setRequestMethod("GET");

			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();

			String line;

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			Object obj = parser.parse(sb.toString());
			JsonObject jsonObj = (JsonObject) obj;
			String city = jsonObj.get("city").toString();
			String locality = jsonObj.get("locality").toString();

			System.out.println("시 : " + city);
			System.out.println("동 : " + locality);

			return city + " " + locality;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
//	    		
//	    	}
//	    	
//	    	
		}
		return "알수 없는 지역";
	}

}

package com.test.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.test.domain.Covid_dangerVO;

public interface CovidAlertMapper {

	public double getCovidLongtitude(HashMap<String, Integer> covid);
	public double getCovidLatitude(HashMap<String, Integer> covid);
	public double getUserLongtitude(HashMap<String, Integer> user);
	public double getUserLatitude(HashMap<String, Integer> user);
	public int getUserorder(int userNum);
	public int getCovidorder(int covidNum);
	public int[] getUserOrders(int userNum);
	public int getUserOrderCnt(int userNum);
	public void insertCovidDangerInfo(Covid_dangerVO covidDangerVO);
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit);
	public double deg2rad(double deg);
	public double rad2deg(double rad);
}

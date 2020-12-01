package com.example.demo.service;

import com.example.demo.domain.Location;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CovidService {

    @Autowired
    LocationRepository locationRepository;

    public List<String> AlertCovid (String uid, Date date)
    {
        // -2~2일간 확진자 이동경로
        List<Location> covoidList=locationRepository.getLocationsByUserIdAndCreated_atBetween(uid,new Timestamp(date.getTime()));
        List<String> alertUser=new ArrayList<>();

        for(int i=0;i<covoidList.size();i++)
        {
            Location covid_loc=covoidList.get(i);
            List<Location> locations=locationRepository.getLocationsByCreated_atEqualsAndUserIdIsNot(covid_loc.getCreated_at(),uid);
            for(int j=0;j<locations.size();j++)
            {
                Location user_loc=locations.get(j);
                // 거리 비교 6피트 = 1.8288m
                double dis=distance(covid_loc.getLatitude(),covid_loc.getLongitude(),user_loc.getLatitude(),user_loc.getLongitude());
                if(dis<=1.8288) // 알림 발생
                    alertUser.add(user_loc.getUser().getId());
            }
        }

        return alertUser;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1609.344;//미터
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}

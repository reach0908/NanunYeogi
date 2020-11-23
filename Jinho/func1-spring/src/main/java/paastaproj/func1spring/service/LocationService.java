package paastaproj.func1spring.service;

import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.repositories.LocationRepository;

import java.util.List;

public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    //위치저장
    public Long save(Location location){
        locationRepository.save(location);
        return location.getId();
    }

    //위치목록불러오기
    public List<Location> showAll(){
        return locationRepository.ShowAll();
    }
}

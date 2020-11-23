package paastaproj.func1spring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.repositories.MemoryLocationRepository;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    LocationService locationService;
    MemoryLocationRepository memoryLocationRepository;

    @BeforeEach
    public void beforeEach(){
        memoryLocationRepository = new MemoryLocationRepository();
        locationService = new LocationService(memoryLocationRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryLocationRepository.clearStore();
    }

    @Test
    void save() {

        //given
        Location location = new Location();
        location.setLatitude("33");
        location.setLongitude("55");

        //when
        locationService.save(location);

        //then
    }

    @Test
    void showAll() {
        //given
        Location location = new Location();
        location.setLatitude("33");
        location.setLongitude("55");
        //when
        locationService.save(location);
        //then
        locationService.showAll();
    }
}
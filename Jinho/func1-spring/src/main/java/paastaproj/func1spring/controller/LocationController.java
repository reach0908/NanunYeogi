package paastaproj.func1spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.service.LocationService;

import java.util.List;

@Controller
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService=locationService;
    }

    //위치받아오기
    @GetMapping("/locations/save")
    public String createForm(){
        return "locations/locationSave";
    }
    //위치저장
    @PostMapping("/locations/save")
    public String save(LocationForm form){
        Location location = new Location();
        location.setLatitude(form.getLatitude());
        location.setLongitude(form.getLongitude());
        locationService.save(location);
        return "redirect:/";
    }
    //위치목록

    @GetMapping("/locations")
    public String list(Model model){
        List<Location> locations = locationService.showAll();
        model.addAttribute("locations",locations);
        return "locations/locationList";
    }
}

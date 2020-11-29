package paastaproj.func1spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.service.LocationService;

import java.util.LinkedList;
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

    @GetMapping("/maps")
    public String map(Model model){

        List<Location> locations = locationService.showAll();

        //경로 출발 및 목적지 설정
        String lat_src = locations.get(0).getLatitude();
        //System.out.println(lat_src);
        String lng_src = locations.get(0).getLongitude();
        //System.out.println(lng_src);
        String way_point = "";
        for (int i=1;i<locations.size()-1;i++){
            String lat = locations.get(i).getLatitude();
            String lng = locations.get(i).getLongitude();
            way_point += lng;
            way_point += ",";
            way_point += lat;
            if(i!=locations.size()-2){
                way_point += ":";
            }

        }
        //System.out.println(lng_src);
        String lat_dest = locations.get(locations.size()-1).getLatitude();
        //System.out.println(lat_dest);
        String lng_dest = locations.get(locations.size()-1).getLongitude();
        //System.out.println(lng_dest);

        List<Location> path_arr = new LinkedList<>();

        //Directions로 경로 리스트 받아오기
        try {
            path_arr = locationService.sendGet("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start="
                    + lng_src + "," + lat_src + "&waypoints=" + way_point +"&goal=" + lng_dest + "," + lat_dest + "&option=trafast");
        }catch (Exception e) {
            e.printStackTrace();
        }
        //경로 전달
        model.addAttribute("locations",path_arr);
        //내가 들린 포인트 전달
        model.addAttribute("myloca",locations);
        //확진자 포인트 전달
        //model.addAttribute("")
        return "locations/map";
    }

}

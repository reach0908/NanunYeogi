package paastaproj.func1spring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import paastaproj.func1spring.repositories.LocationRepository;
import paastaproj.func1spring.repositories.MeberRepository;
import paastaproj.func1spring.repositories.MemoryLocationRepository;
import paastaproj.func1spring.repositories.MemoryMemberRepository;
import paastaproj.func1spring.service.LocationService;
import paastaproj.func1spring.service.MemberService;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService(){
        return new MemberService(meberRepository());
    }

    @Bean
    public MeberRepository meberRepository(){
        return new MemoryMemberRepository();
    }

    @Bean
    public LocationService locationService(){return new LocationService(locationRepository());}

    @Bean
    public LocationRepository locationRepository(){return new MemoryLocationRepository();}
}

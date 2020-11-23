package paastaproj.func1spring.repositories;

import paastaproj.func1spring.domain.Location;

import java.util.List;

public interface LocationRepository {

    Location save(Location location);
    List<Location> ShowAll();
}

package paastaproj.func1spring.repositories;

import paastaproj.func1spring.domain.Location;
import paastaproj.func1spring.domain.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryLocationRepository implements LocationRepository{

    private static Map<Long, Location> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Location save(Location location) {
        location.setId(++sequence);
        store.put(location.getId(),location);
        return location;
    }

    @Override
    public List<Location> ShowAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }
}

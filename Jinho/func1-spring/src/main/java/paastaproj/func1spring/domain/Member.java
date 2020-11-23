package paastaproj.func1spring.domain;

import java.util.List;

public class Member {
    private Long id;
    private String name;
    private List<String> locations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

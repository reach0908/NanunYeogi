package paastaproj.func1spring.controller;

public class LocationForm {
    private String latitude;
    private String longitude;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude(){
        return latitude;
    }
    public String getLongitude(){
        return longitude;
    }
}

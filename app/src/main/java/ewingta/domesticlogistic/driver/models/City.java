package ewingta.domesticlogistic.driver.models;

public class City {
    private long id;
    private String state_id;
    private String location_name;
    private String country_id;
    private String published;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", state_id='" + state_id + '\'' +
                ", location_name='" + location_name + '\'' +
                ", country_id='" + country_id + '\'' +
                ", published='" + published + '\'' +
                '}';
    }
}

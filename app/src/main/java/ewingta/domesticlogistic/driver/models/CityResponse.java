package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class CityResponse {
    private String status;
    private List<City> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<City> getCities() {

        if (cities == null) {
            cities = new ArrayList<>();
        }

        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityResponse{" +
                "status='" + status + '\'' +
                ", cities=" + cities +
                '}';
    }
}

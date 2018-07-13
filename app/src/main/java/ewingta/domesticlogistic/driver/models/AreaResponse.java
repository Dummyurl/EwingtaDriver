package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class AreaResponse {
    private List<Area> locations;
    private String status;

    public List<Area> getLocations() {

        if (locations == null) {
            locations = new ArrayList<>();
        }

        return locations;
    }

    public void setLocations(List<Area> locations) {
        this.locations = locations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Area{" +
                "locations=" + locations +
                ", status='" + status + '\'' +
                '}';
    }
}

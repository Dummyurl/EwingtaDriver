package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class TimeResponse {
    private List<Time> timeslist;
    private String status;

    public List<Time> getTimeslist() {

        if (timeslist == null) {
            timeslist = new ArrayList<>();
        }

        return timeslist;
    }

    public void setTimeslist(List<Time> timeslist) {
        this.timeslist = timeslist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimeResponse{" +
                "timeslist=" + timeslist +
                ", status='" + status + '\'' +
                '}';
    }
}

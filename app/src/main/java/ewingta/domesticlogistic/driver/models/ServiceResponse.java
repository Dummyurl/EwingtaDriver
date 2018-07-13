package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {
    private List<Service> servicelist;
    private String status;

    public List<Service> getServicelist() {
        return servicelist;
    }

    public void setServicelist(List<Service> servicelist) {

        if (servicelist == null) {
            servicelist = new ArrayList<>();
        }

        this.servicelist = servicelist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "servicelist=" + servicelist +
                ", status='" + status + '\'' +
                '}';
    }
}

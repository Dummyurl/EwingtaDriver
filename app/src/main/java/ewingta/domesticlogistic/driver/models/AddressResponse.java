package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class AddressResponse {
    private String status;
    private List<Address> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Address> getMessage() {

        if (message == null) {
            message = new ArrayList<>();
        }

        return message;
    }

    public void setMessage(List<Address> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AddressResponse{" +
                "status='" + status + '\'' +
                ", message=" + message +
                '}';
    }
}

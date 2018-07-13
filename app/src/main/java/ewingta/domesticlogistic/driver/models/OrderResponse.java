package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
    private List<Order> message;
    private String status;

    public List<Order> getMessage() {

        if (message == null) {
            message = new ArrayList<>();
        }

        return message;
    }

    public void setMessage(List<Order> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

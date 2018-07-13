package ewingta.domesticlogistic.driver.models;

public class PriceResponse {
    private String price;
    private String status;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PriceResponse{" +
                "price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

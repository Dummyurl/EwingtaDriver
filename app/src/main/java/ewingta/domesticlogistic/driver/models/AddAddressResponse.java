package ewingta.domesticlogistic.driver.models;

public class AddAddressResponse {
    private String Statuss;
    private String status;

    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddAddressResponse{" +
                "Statuss='" + Statuss + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

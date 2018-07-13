package ewingta.domesticlogistic.driver.models;

import java.util.ArrayList;
import java.util.List;

public class PriceListResponse {
    private String status;
    private List<Price> pricelist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Price> getPricelist() {

        if (pricelist == null) {
            pricelist = new ArrayList<>();
        }

        return pricelist;
    }

    public void setPricelist(List<Price> pricelist) {
        this.pricelist = pricelist;
    }

    @Override
    public String toString() {
        return "PriceListResponse{" +
                "status='" + status + '\'' +
                ", pricelist=" + pricelist +
                '}';
    }
}

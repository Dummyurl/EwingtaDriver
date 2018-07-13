package ewingta.domesticlogistic.driver.models;

public class Price {
    private String service_id;
    private String id;
    private String gst;
    private String price_name;
    private String service_name;
    private String published;
    private String price_value;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getPrice_value() {
        return price_value;
    }

    public void setPrice_value(String price_value) {
        this.price_value = price_value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "service_id='" + service_id + '\'' +
                ", id='" + id + '\'' +
                ", gst='" + gst + '\'' +
                ", price_name='" + price_name + '\'' +
                ", service_name='" + service_name + '\'' +
                ", published='" + published + '\'' +
                ", price_value='" + price_value + '\'' +
                '}';
    }
}

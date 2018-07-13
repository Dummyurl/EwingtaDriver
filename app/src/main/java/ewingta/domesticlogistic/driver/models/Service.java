package ewingta.domesticlogistic.driver.models;

public class Service {
    private long id;
    private String service_content;
    private String service_key;
    private String service_name;
    private String published;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getService_content() {
        return service_content;
    }

    public void setService_content(String service_content) {
        this.service_content = service_content;
    }

    public String getService_key() {
        return service_key;
    }

    public void setService_key(String service_key) {
        this.service_key = service_key;
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

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", service_content='" + service_content + '\'' +
                ", service_key='" + service_key + '\'' +
                ", service_name='" + service_name + '\'' +
                ", published='" + published + '\'' +
                '}';
    }
}

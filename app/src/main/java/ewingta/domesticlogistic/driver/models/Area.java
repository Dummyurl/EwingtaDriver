package ewingta.domesticlogistic.driver.models;

public class Area {
    private long id;
    private String state_id;
    private String admin_password;
    private String country_id;
    private String user_id;
    private String published;
    private String admin_email;
    private String admin_name;
    private String admin_phone;
    private String location_id;
    private String branch_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_phone() {
        return admin_phone;
    }

    public void setAdmin_phone(String admin_phone) {
        this.admin_phone = admin_phone;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    @Override
    public String toString() {
        return "AreaResponse{" +
                "id=" + id +
                ", state_id='" + state_id + '\'' +
                ", admin_password='" + admin_password + '\'' +
                ", country_id='" + country_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", published='" + published + '\'' +
                ", admin_email='" + admin_email + '\'' +
                ", admin_name='" + admin_name + '\'' +
                ", admin_phone='" + admin_phone + '\'' +
                ", location_id='" + location_id + '\'' +
                ", branch_name='" + branch_name + '\'' +
                '}';
    }
}

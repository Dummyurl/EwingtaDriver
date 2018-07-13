package ewingta.domesticlogistic.driver.models;

public class LoginResponse {
    private String email;
    private String status;
    private String name;
    private String userid;
    private String mobile;
    private String message;
    private String error_description;

    //http://domesticlogistics.in/beta/index.php?option=com_jbackend&view=request&module=user&action=post&resource=login&username=9030589619&password=123456


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}

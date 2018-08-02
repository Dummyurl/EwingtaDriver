package ewingta.domesticlogistic.driver.models;

/**
 * Created by saxxis25 on 7/30/2018.
 */

public class VerifyResponse
{
    private String status;
    private String message;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}

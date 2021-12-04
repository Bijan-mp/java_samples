package model.to;

/**
 * Created with IntelliJ IDEA.
 * User: bij
 * Date: 1/9/13
 * Time: 3:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationInformationTO {
    private long id;
    private long customerId;
    private String userName;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

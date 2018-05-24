package po;

public class User {
    private String username;
    private String passward;
    private String passward_salt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public String getPassward_salt() {
        return passward_salt;
    }

    public void setPassward_salt(String passward_salt) {
        this.passward_salt = passward_salt;
    }
}

package ar.edu.itba.paw.model;

/**
 * Created by daniel on 4/3/17.
 */
public class UserMetadata {

    private long userId;
    private String lastStorageUpdated; // String?
    private String token; // String
    private String password;

    public UserMetadata(long userId, String lastStorageUpdated, String token, String password) {
        this.userId = userId;
        this.lastStorageUpdated = lastStorageUpdated;
        this.token = token;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public String getLastStorageUpdated() {
        return lastStorageUpdated;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setLastStorageUpdated(String lastStorageUpdated) {
        this.lastStorageUpdated = lastStorageUpdated;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

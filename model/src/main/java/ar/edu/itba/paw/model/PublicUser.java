package ar.edu.itba.paw.model;

/**
 * Created by julian on 3/26/17.
 */
public class PublicUser {
    private String username;
    private long id;
    private String msg;

    public PublicUser(String username, long id, String msg) {
        this.username = username;
        this.id = id;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}

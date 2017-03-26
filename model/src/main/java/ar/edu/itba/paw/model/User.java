package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 23/03/17.
 */
public class User {

    private long id;
    private String username;
    private String password;


    public User(long id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

}

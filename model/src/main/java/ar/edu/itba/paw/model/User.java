package ar.edu.itba.paw.model;

public class User {

    private String username;
    private String loginToken;
    private long id;

    public User( String username, long id,String loginToken){
        this.username = username;
        this.loginToken = loginToken;
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginToken() {
        return loginToken;
    }
}

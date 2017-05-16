package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 23/03/17.
 */

public class User {

    private long id;
    private String username;
    private String password;
    private String profileImage;
    private double score;

    public User(long id, String username, String password, String profileImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        score = 0;
    }

    public User(long id, String username, String password, String profileImage, double score) {
        this(id,username,password,profileImage);
        this.score = score;
    }

    public String getProfileImage() {
        return profileImage;
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

    public double getScore() {
        return score;
    }


}

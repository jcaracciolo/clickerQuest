package ar.edu.itba.paw.model;

public class User {

    private long id;
    private String username;
    private String password;
    private String profileImage;
    private double score;

    // Null indicates no clan
    private Integer clanId;

    public User(long id, String username, String password, String profileImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.clanId = null;
        this.score = 0;
    }

    public User(long id, String username, String password, String profileImage, double score, Integer clanId) {
        this(id,username,password,profileImage);
        this.clanId = clanId;
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

    public Integer getClanIdentifier() {
        return clanId;
    }
}

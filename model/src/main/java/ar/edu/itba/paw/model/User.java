package ar.edu.itba.paw.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class User {

    private long id;
    private String username;
    private String password;
    private String profileImage;
    private double score;

    @Nullable
    private Integer clanId;

    public User(long id,  @NotNull String username, @NotNull String password, @NotNull String profileImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.clanId = null;
        this.score = 0;
    }

    public User(long id,  @NotNull String username,  @NotNull String password,  @NotNull String profileImage,
                double score, @Nullable Integer clanId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (Double.compare(user.score, score) != 0) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!profileImage.equals(user.profileImage)) return false;
        return clanId != null ? clanId.equals(user.clanId) : user.clanId == null;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

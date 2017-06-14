package ar.edu.itba.paw.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(sequenceName = "users_userid_seq",name = "users_userid_seq",allocationSize = 1)
    @Column(name = "userid")
    private  long id;

    @Column(length = 100, nullable = false, unique = true)
    private  String username;

    @Column(length = 100, nullable = false)
    private  String password;

    @Column(name = "profileimage",length = 100, nullable = false)
    private  String profileImage;

    @Column(nullable = false)
    private  double score;

    @Column(name = "clanid")
    @Nullable
    private Integer clanId;

    public User(){
        //Just for hibernate :D
    }

    public User(long id,  @NotNull String username, @NotNull String password, @NotNull String profileImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.clanId = null;
        this.score = 0;
    }

    public User(@NotNull String username,  @NotNull String password,  @NotNull String profileImage,
                double score, @Nullable Integer clanId) {
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.clanId = clanId;
        this.score = score;
    }

    public User(long id, String username, String password, String profileImage, double score, Integer id1) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.clanId =id1;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", score=" + score +
                ", clanId=" + clanId +
                '}';
    }
}

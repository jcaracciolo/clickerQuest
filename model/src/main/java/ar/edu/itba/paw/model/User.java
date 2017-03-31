package ar.edu.itba.paw.model;

import java.util.List;

/**
 * Created by juanfra on 23/03/17.
 */
public class User {

    private long id;
    private String username;
    private String password;
    private String profileImage;
    private List<Amount> storage;
    private List<Production> productions;

    public User(long id, String username, String password, String profileImage, List<Amount> storage, List<Production> productions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
        this.storage = storage;
        this.productions = productions;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public List<Amount> getStorage() {
        return storage;
    }

    public List<Production> getProductions() {
        return productions;
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

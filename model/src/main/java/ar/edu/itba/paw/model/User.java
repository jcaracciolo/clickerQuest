package ar.edu.itba.paw.model;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 23/03/17.
 */

public class User {

    private long id;
    private String username;
    private String password;
    private String profileImage;

    private ResourcePackage productions;
    private ResourcePackage storage;
    private Collection<Factory> factories;

    public User(long id, String username, String password, String profileImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
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


}

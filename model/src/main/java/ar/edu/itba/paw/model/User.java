package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 23/03/17.
 */
public class User {

    private String name;
    private long id;

    public User(String name, long id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}

package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 23/03/17.
 */
public class User {

    private String name;
    private int id;

    public User(String name,int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}

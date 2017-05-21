package ar.edu.itba.paw.model.clan;

import ar.edu.itba.paw.model.User;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by juanfra on 17/05/17.
 */
public class Clan implements Iterable<User> {
    Set<User> users = new TreeSet<User>((u1,u2) ->  u1.getScore()>u2.getScore()?1:-1 );

    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public boolean addUser(User u) {
        return users.add(u);
    }

    Clan(Collection<User> users,int id,String name) {
        this.name = name;
        this.id = id;
        this.users.addAll(users);
    }

    public User getUser(long userId) {
        return users.stream().filter((u) -> u.getId() == userId).findAny().orElse(null);
    }

    public boolean isInClan(long userId){
        return users.stream().filter((u)->u.getId() == userId).map((u)->true).findAny().orElse(false);
    }

    public boolean isInClan(User user) {
        return isInClan(user.getId());
    }

    public double getClanScore() {
        return users.stream().map(User::getScore).reduce((d1,d2)->d1+d2).orElse(0D);
    }

    @Override
    public Iterator<User> iterator() {
        return new Iterator<User>() {
            private Iterator<User> it = users.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public User next() {
                return it.next();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super User> action) {
        users.forEach(action);
    }

    @Override
    public Spliterator<User> spliterator() {
        return users.spliterator();
    }

    public Stream<User> stream(){
        return users.stream();
    }

    public Set<User> getUsers() {
        return users;
    }

    public boolean containsUser(long userId) {
        return stream().filter((u)->u.getId()==userId).map((u)->true).findAny().orElse(false);
    }

    public int getId() {
        return id;
    }
}

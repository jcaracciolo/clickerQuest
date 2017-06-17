package ar.edu.itba.paw.model.clan;

import ar.edu.itba.paw.model.User;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by juanfra on 17/05/17.
 */
@Entity
@Table(name = "clans")
public class Clan implements Iterable<User> {
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "clanid")
    private final Set<User> users = new TreeSet<User>((u1,u2) ->  u1.getScore()<u2.getScore()?1:-1 );

    @Id
    @Column(name = "clanid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clans_clanid_seq")
    @SequenceGenerator(sequenceName = "clans_clanid_seq",name = "clans_clanid_seq",allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    Clan(){}

    Clan(@NotNull Collection<User> users, int id,@NotNull String name) {
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

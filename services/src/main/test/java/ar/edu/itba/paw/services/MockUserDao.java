package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by juanfra on 13/04/17.
 */
class MockUserDao implements UserDao {

    private List<MockUserDaoData> tables = new ArrayList<>();
    private int counter = 0;

    private class MockUserDaoData {
        User user;
        Wealth wealth;
        List<Factory> factories;

        private MockUserDaoData(User user, Wealth wealth, List<Factory> factories) {
            this.user = user;
            this.wealth = wealth;
            this.factories = factories;
        }
    }

    private MockUserDaoData getUserMockData(long id){
        Optional<MockUserDaoData> maybeUser =
                tables.stream().filter(
                        (t) -> t.user.getId() == id
                ).findAny();

        if(maybeUser.isPresent()){
            return maybeUser.get();
        } else {
            return null;
        }
    }

    @Override
    public User findById(long id) {
        MockUserDaoData d = getUserMockData(id);
        if(d==null) return null;
        return d.user;
    }

    @Override
    public User findByUsername(String username) {
        Optional<MockUserDaoData> maybeUser =
                tables.stream().filter(
                        (t) -> t.user.getUsername().equals(username)
                ).findAny();

        if(maybeUser.isPresent()){
            return maybeUser.get().user;
        } else {
            return null;
        }
    }

    @Override
    public User create(String username, String password, String profileImage) {
        if(findByUsername(username) != null) {
            throw new RuntimeException("Username already in use");
        }

        User u = new User(counter,username,password,profileImage);
        counter++;

        PackageBuilder<Storage> sBuilder = Storage.packageBuilder();
        PackageBuilder<Productions> pBuilder = Productions.packageBuilder();
        Calendar now = Calendar.getInstance();
        Arrays.stream(ResourceType.values()).forEach(
                (r) -> {
                    sBuilder.putItemWithDate(r,0D,now);
                    pBuilder.putItem(r,0D);
                }
        );
        Wealth w = new Wealth(u.getId(),sBuilder.buildPackage(),pBuilder.buildPackage());

        List<Factory> factories = Arrays.stream(FactoryType.values()).map(
                (f) -> new Factory(u.getId(),f,0,1,1,1,Upgrade.getUpgrade(f,0))
        ).collect(Collectors.toList());

        tables.add(new MockUserDaoData(u,w,factories));

        return u;
    }

    @Override
    public String getProfileImage(long userid) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.user.getProfileImage();
    }

    @Override
    public Productions getUserProductions(long userid) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.wealth.getProductions();
    }

    @Override
    public Storage getUserStorage(long userid) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.wealth.getStorage();
    }

    @Override
    public Collection<Factory> getUserFactories(long userid) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.factories;
    }

    @Override
    public Factory getUserFactory(long userid, FactoryType type) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.factories
                .stream().filter(
                        (f) -> f.getType() == type
                ).findAny().get();
    }

    @Override
    public Factory update(Factory f) {
        MockUserDaoData d = getUserMockData(f.getUserid());
        if(d==null) return null;

        d.factories.replaceAll(
                (o) -> {
                    if(o.getType() == f.getType()) {
                        return copyFactory(f);
                    } else {
                        return o;
                    }
                }
        );

        return f;
    }

    @Override
    public Wealth getUserWealth(long userid) {
        MockUserDaoData d = getUserMockData(userid);
        if(d==null) return null;
        return d.wealth;
    }

    @Override
    public Wealth update(Wealth w) {
        MockUserDaoData d = getUserMockData(w.getUserid());
        if(d==null) return null;
        d.wealth = copyWealth(w);
        return d.wealth;
    }

    private Factory copyFactory(Factory f) {
        return new Factory(
                f.getUserid(),
                f.getType(),
                f.getAmount(),
                f.getInputReduction(),
                f.getOutputMultiplier(),
                f.getCostReduction(),
                Upgrade.getUpgrade(f.getType(),f.getUpgrade().getLevel())
        );
    }

    private Wealth copyWealth(Wealth w) {
        return new Wealth(
                w.getUserid(),
                copyStorage(w.getStorage()),
                copyProductions(w.getProductions()));
    }

    private Storage copyStorage(Storage s) {
        PackageBuilder<Storage> builder = Storage.packageBuilder();
        s.rawMap().forEach(builder::putItem);
        s.getLastUpdated().forEach(builder::setLastUpdated);
        return builder.buildPackage();
    }

    private Productions copyProductions(Productions p) {
        PackageBuilder<Productions> builder = Productions.packageBuilder();
        p.rawMap().forEach(builder::putItem);
        return builder.buildPackage();
    }
}

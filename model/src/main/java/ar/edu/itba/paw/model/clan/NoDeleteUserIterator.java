package ar.edu.itba.paw.model.clan;

import ar.edu.itba.paw.model.User;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by juanfra on 17/05/17.
 */
public class NoDeleteUserIterator implements Iterator<User>{
    Iterator<User> it;
    NoDeleteUserIterator(Iterator<User> it){
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public User next() {
        return it.next();
    }
}
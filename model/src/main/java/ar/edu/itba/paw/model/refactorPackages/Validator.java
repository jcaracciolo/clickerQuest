package ar.edu.itba.paw.model.refactorPackages;

/**
 * Created by juanfra on 08/04/17.
 */
public interface Validator<T> {
    public boolean validates(T t);
}

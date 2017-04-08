package ar.edu.itba.paw.model.refactorPackages;

/**
 * Created by juanfra on 08/04/17.
 */
public interface Creator<T extends ResourcePackage> {
    public T create(PackageBuilder<T> packageBuilder);
}

package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.FactoryService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Upgrade;

/**
 * Created by juanfra on 02/04/17.
 */
public class FactoryServiceImpl implements FactoryService {

    public Upgrade getUpgradeById(long id) {
        return new Upgrade(id,"Super Upgrade",2,300);
    }

    public FactoryType getFactoryById(long id) {
        return FactoryType.getById(id);
    }
}

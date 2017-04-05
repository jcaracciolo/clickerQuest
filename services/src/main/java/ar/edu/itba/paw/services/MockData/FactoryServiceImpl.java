package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.FactoryService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Upgrade;

/**
 * Created by juanfra on 02/04/17.
 */
public class FactoryServiceImpl implements FactoryService {

    public Upgrade getUpgradeById(int id) {
        return new Upgrade(2, id,"Super Upgrade", 300);
    }

    public FactoryType getFactoryById(int id) {
        return FactoryType.fromId(id);
    }
}

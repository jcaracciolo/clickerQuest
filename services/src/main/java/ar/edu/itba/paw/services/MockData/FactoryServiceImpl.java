package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.FactoryService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Upgrade;

/**
 * Created by juanfra on 02/04/17.
 */
public class FactoryServiceImpl implements FactoryService {

    public Upgrade getUpgrade(FactoryType type, int level){
        return Upgrade.getUpgrade(type,level);
    }
    public FactoryType getFactoryById(int id) {
        return FactoryType.fromId(id);
    }
}

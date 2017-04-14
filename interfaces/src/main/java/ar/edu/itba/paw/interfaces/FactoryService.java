package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Upgrade;

/**
 * Created by juanfra on 02/04/17.
 */
public interface FactoryService {
    Upgrade getUpgrade(FactoryType type,int level);
    FactoryType getFactoryById(int id);
}

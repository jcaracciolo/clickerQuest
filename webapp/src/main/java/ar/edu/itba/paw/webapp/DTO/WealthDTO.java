package ar.edu.itba.paw.webapp.DTO;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.Wealth;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class WealthDTO {

    @XmlElement(name = "storage")
    private Map<ResourceType,Double> storage;

    @XmlElement(name = "productions")
    private Map<ResourceType,Double> productions;

    public WealthDTO(){}

    public WealthDTO(Wealth wealth) {
        storage = wealth.getStorage().rawMap();
        productions = wealth.getProductions().rawMap();
    }

    public Map<ResourceType, Double> getStorage() {
        return storage;
    }

    public Map<ResourceType, Double> getProductions() {
        return productions;
    }
}

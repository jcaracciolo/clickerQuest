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
    private MapDTO<ResourceType,Double> storage;

    @XmlElement(name = "productions")
    private MapDTO<ResourceType,Double> productions;

    public WealthDTO(){}

    public WealthDTO(Wealth wealth) {
        storage = new MapDTO<>(wealth.getStorage().rawMap());
        productions = new MapDTO<>(wealth.getProductions().rawMap());
    }

    public MapDTO<ResourceType, Double> getStorage() {
        return storage;
    }

    public MapDTO<ResourceType, Double> getProductions() {
        return productions;
    }
}

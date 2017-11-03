package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.webapp.DTO.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class WealthDTO {

    static String url = "users/%s/wealth";

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

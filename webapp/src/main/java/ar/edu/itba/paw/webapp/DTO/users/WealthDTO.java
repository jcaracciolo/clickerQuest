package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.webapp.DTO.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlType(name = "Wealth")
@XmlRootElement
public class WealthDTO {

    static String url = "users/%s/wealth";

    @XmlElement(name = "user_id")
    private long userID;

    @XmlElement(name = "storage")
    private MapDTO<ResourceType,Double> storage;

    @XmlElement(name = "productions")
    private MapDTO<ResourceType,Double> productions;

    public WealthDTO(){}

    public WealthDTO(Wealth wealth, long userID) {
        this.userID = userID;
        storage = new MapDTO<>(wealth.getStorage().rawMap());
        productions = new MapDTO<>(wealth.getProductions().rawMap());
    }

}

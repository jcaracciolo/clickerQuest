package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.webapp.DTO.map.MapDTO;

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

    @XmlElement(name = "resources")
    private MapDTO resources;

    public WealthDTO(){}

    public WealthDTO(Wealth wealth, long userID) {
        this.userID = userID;
        this.resources = new MapDTO(wealth.getStorage().rawMap(), wealth.getProductions().rawMap());

    }

}

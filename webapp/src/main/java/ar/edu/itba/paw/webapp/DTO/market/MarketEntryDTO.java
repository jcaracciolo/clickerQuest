package ar.edu.itba.paw.webapp.DTO.market;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarketEntryDTO {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "price")
    private Integer price;

    public MarketEntryDTO(){}

    public MarketEntryDTO(int id, Integer price) {
        this.id = id;
        this.price = price;
    }
}

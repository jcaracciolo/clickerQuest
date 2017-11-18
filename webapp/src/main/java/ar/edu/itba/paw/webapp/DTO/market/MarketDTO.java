package ar.edu.itba.paw.webapp.DTO.market;

import ar.edu.itba.paw.model.ResourceType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;

@XmlRootElement
public class MarketDTO {
    static String url = "market";

    @XmlElement(name = "CIRCUITS")
     MarketEntryDTO CIRCUITS;
    @XmlElement(name = "CARDBOARD")
     MarketEntryDTO CARDBOARD;
    @XmlElement(name = "COPPER_CABLE")
     MarketEntryDTO COPPER_CABLE;
    @XmlElement(name = "COPPER")
     MarketEntryDTO COPPER;
    @XmlElement(name = "METAL_SCRAP")
     MarketEntryDTO METAL_SCRAP;
    @XmlElement(name = "RUBBER")
     MarketEntryDTO RUBBER;
    @XmlElement(name = "TIRES")
     MarketEntryDTO TIRES;
    @XmlElement(name = "IRON")
     MarketEntryDTO IRON;
    @XmlElement(name = "PEOPLE")
     MarketEntryDTO PEOPLE;
    @XmlElement(name = "MONEY")
     MarketEntryDTO MONEY;
    @XmlElement(name = "GOLD")
     MarketEntryDTO GOLD;
    @XmlElement(name = "PLASTIC")
     MarketEntryDTO PLASTIC;
    @XmlElement(name = "POWER")
     MarketEntryDTO POWER;

    public MarketDTO() {
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field f: fields) {
            ResourceType res = ResourceType.fromName(f.getName()).orElse(null);
            if(res != null) {
                try {
                    f.set(this, new MarketEntryDTO(res.getId(), res.getPrice()));
                }catch (IllegalAccessException e) {
                    //TODO log here
                }
            }
        }
    }

}

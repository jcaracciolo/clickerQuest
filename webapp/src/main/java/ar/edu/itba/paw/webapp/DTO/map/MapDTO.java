package ar.edu.itba.paw.webapp.DTO.map;

import ar.edu.itba.paw.model.ResourceType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class MapDTO {
    @XmlElement(name = "CIRCUITS")
     ResourceEntryDTO CIRCUITS;
    @XmlElement(name = "CARDBOARD")
     ResourceEntryDTO CARDBOARD;
    @XmlElement(name = "COPPER_CABLE")
     ResourceEntryDTO COPPER_CABLE;
    @XmlElement(name = "COPPER")
     ResourceEntryDTO COPPER;
    @XmlElement(name = "METAL_SCRAP")
     ResourceEntryDTO METAL_SCRAP;
    @XmlElement(name = "RUBBER")
     ResourceEntryDTO RUBBER;
    @XmlElement(name = "TIRES")
     ResourceEntryDTO TIRES;
    @XmlElement(name = "IRON")
     ResourceEntryDTO IRON;
    @XmlElement(name = "PEOPLE")
     ResourceEntryDTO PEOPLE;
    @XmlElement(name = "MONEY")
     ResourceEntryDTO MONEY;
    @XmlElement(name = "GOLD")
     ResourceEntryDTO GOLD;
    @XmlElement(name = "PLASTIC")
     ResourceEntryDTO PLASTIC;
    @XmlElement(name = "POWER")
     ResourceEntryDTO POWER;

    public MapDTO(){}

    public MapDTO(Map<ResourceType, Double> storage, Map<ResourceType, Double> productions) {
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field f: fields) {
            ResourceType res = ResourceType.fromName(f.getName()).orElse(null);
            if(res != null) {
                Double resStorage = null;
                Double resProduction = null;
                if(storage!= null) resStorage = storage.get(res);
                if(productions!= null) resProduction = productions.get(res);
                if(resStorage!=null || resProduction !=null) {
                    try {
                        f.set(this, new ResourceEntryDTO(res.getId(), resStorage,resProduction));
                    }catch (IllegalAccessException e) {
                        //TODO log here
                    }
                }
            }
        }
    }
}

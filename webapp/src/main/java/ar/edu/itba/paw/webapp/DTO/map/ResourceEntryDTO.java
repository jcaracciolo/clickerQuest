package ar.edu.itba.paw.webapp.DTO.map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceEntryDTO {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "storage")
    private Double storage;
    @XmlElement(name = "production")
    private Double production;

    public ResourceEntryDTO(int id, Double storage, Double production) {
        this.id = id;
        this.storage = storage;
        this.production = production;
    }

    public ResourceEntryDTO(){}
}

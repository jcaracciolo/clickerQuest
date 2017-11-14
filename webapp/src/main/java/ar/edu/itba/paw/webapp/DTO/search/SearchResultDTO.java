package ar.edu.itba.paw.webapp.DTO.search;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class SearchResultDTO {


    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "name")
    private String name;


    public SearchResultDTO(){}

    public SearchResultDTO(SearchResultType type, String name) {
        this.type = type.name;
        this.name = name;
    }

}

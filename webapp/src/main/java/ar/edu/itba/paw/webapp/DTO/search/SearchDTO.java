package ar.edu.itba.paw.webapp.DTO.search;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.webapp.DTO.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class SearchDTO {

    @XmlElement(name = "results")
    private Collection<SearchResultDTO> results;

    public SearchDTO(){}
    public SearchDTO(Collection<SearchResultDTO> results) {
        this.results = results;
    }
}

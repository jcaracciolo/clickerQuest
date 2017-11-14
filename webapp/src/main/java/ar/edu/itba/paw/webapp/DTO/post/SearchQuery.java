package ar.edu.itba.paw.webapp.DTO.post;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchQuery {
    @XmlElement(name = "search")
    public String query;
}


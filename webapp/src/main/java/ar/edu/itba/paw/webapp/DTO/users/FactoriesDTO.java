package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.Factory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
public class FactoriesDTO {

    public static String url = "users/%s/factories";

    @XmlElement(name = "factories")
    private List<FactoryDTO> factories;

    public FactoriesDTO(){}

    public FactoriesDTO(Collection<Factory> factories, URI baseUri) {
        this.factories = factories.stream().map(f -> new FactoryDTO(f,baseUri)).collect(Collectors.toList());
    }
}

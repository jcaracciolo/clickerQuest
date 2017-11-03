package ar.edu.itba.paw.webapp.DTO.market;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.webapp.DTO.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

import static java.util.stream.Collectors.toMap;

@XmlRootElement
public class MarketDTO {
    static String url = "market";

    @XmlElement(name = "prices")
    private MapDTO<String,Integer> prices;

    public MarketDTO() {
        prices = new MapDTO(Arrays.asList(ResourceType.values()).stream()
                .collect(toMap(ResourceType::toString,ResourceType::getPrice)));
    }

}

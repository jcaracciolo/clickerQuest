package ar.edu.itba.paw.webapp.DTO.factories;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.model.packages.Implementations.FactoryCost;
import ar.edu.itba.paw.webapp.DTO.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

import static java.util.stream.Collectors.toMap;

@XmlRootElement
public class BuyLimitsDTO {
    public static String url = "factories/%s/buyLimits";

    @XmlElement(name = "user_id")
    private long userID;

    @XmlElement(name = "factory_id")
    private int factoryID;

    @XmlElement(name = "max")
    private long max;

    @XmlElement(name = "cost_max")
    private MapDTO<ResourceType,Double> costMax;

    @XmlElement(name = "cost_1")
    private MapDTO<ResourceType,Double> cost1;

    @XmlElement(name = "cost_10")
    private MapDTO<ResourceType,Double> cost10;

    @XmlElement(name = "cost_100")
    private MapDTO<ResourceType,Double> cost100;

    public BuyLimitsDTO(BuyLimits buyLimits, long userID, int factoryID) {
        this.userID = userID;
        this.factoryID = factoryID;
        max = buyLimits.getMaxFactories();
        if(max>0) costMax = new MapDTO<>(buyLimits.getCostMax().rawMap());
        cost1 = new MapDTO<>(buyLimits.getCost1().rawMap());
        cost10 = new MapDTO<>(buyLimits.getCost10().rawMap());
        cost100 = new MapDTO<>(buyLimits.getCost100().rawMap());
    }

    public BuyLimitsDTO(){}

}

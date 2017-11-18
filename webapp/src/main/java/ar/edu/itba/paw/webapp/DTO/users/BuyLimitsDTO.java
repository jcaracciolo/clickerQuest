package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.BuyLimits;
import ar.edu.itba.paw.webapp.DTO.map.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
public class BuyLimitsDTO {
    public static String url = "/users/%s/factories/%s/buyLimits";

    @XmlElement(name = "user_id")
    private long userID;

    @XmlElement(name = "factory_id")
    private int factoryID;

    @XmlElement(name = "max")
    private long max;

    @XmlElement(name = "cost_max")
    private MapDTO costMax;

    @XmlElement(name = "cost_1")
    private MapDTO cost1;

    @XmlElement(name = "cost_10")
    private MapDTO cost10;

    @XmlElement(name = "cost_100")
    private MapDTO cost100;

    public BuyLimitsDTO(BuyLimits buyLimits, long userID, int factoryID) {
        this.userID = userID;
        this.factoryID = factoryID;
        FactoryType factoryType = FactoryType.fromId(factoryID);
        if(factoryType!=null) {
            max = buyLimits.getMaxFactories();
            if(max>0) costMax = new MapDTO(buyLimits.getCostMax().rawMap(), multiplyProductions(factoryType.getBaseRecipe().rawMap(), max));
            cost1 = new MapDTO(buyLimits.getCost1().rawMap(), factoryType.getBaseRecipe().rawMap());
            cost10 = new MapDTO(buyLimits.getCost10().rawMap(), multiplyProductions(factoryType.getBaseRecipe().rawMap(), 10));
            cost100 = new MapDTO(buyLimits.getCost100().rawMap(), multiplyProductions(factoryType.getBaseRecipe().rawMap(), 100));
        } else {
            throw new IllegalArgumentException("No factory with the ID " + factoryID);
        }

    }

    public Map<ResourceType, Double> multiplyProductions(Map<ResourceType, Double> map, long factor) {
        for (ResourceType k: map.keySet()) {
            map.put(k, map.get(k) * factor);
        }
        return map;
    }

    public BuyLimitsDTO(){}

}

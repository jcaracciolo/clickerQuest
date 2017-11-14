package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.webapp.DTO.factories.BuyLimitsDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class FactoryDTO {

    public static String url = "users/%s/factories/%s";

    @XmlElement(name = "type_id")
    private Integer typeID;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "amount")
    private double amount;

    @XmlElement(name = "input_reduction")
    private double inputReduction;

    @XmlElement(name = "output_multiplier")
    private double outputMultiplier;

    @XmlElement(name = "cost_reduction")
    private double costReduction;

    @XmlElement(name = "level")
    private int level;

    @XmlElement(name = "upgradeURL")
    private URI upgradeURL;

    @XmlElement(name = "buyLimits_url")
    private URI buyLimitsURL;

    public FactoryDTO(){}

    public FactoryDTO(Factory factory, URI baseUri) {
        typeID = factory.getType().getId();
        type = factory.getType().getNameCode();
        amount = factory.getAmount();
        inputReduction = factory.getInputReduction();
        outputMultiplier = factory.getOutputMultiplier();
        costReduction = factory.getCostReduction();
        level = factory.getLevel();
        upgradeURL = baseUri.resolve(String.format(UpgradeDTO.url, factory.getUserid(), factory.getType().getId()));
        buyLimitsURL = baseUri.resolve(String.format(BuyLimitsDTO.url, factory.getType().getId()));

    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getInputReduction() {
        return inputReduction;
    }

    public double getOutputMultiplier() {
        return outputMultiplier;
    }

    public double getCostReduction() {
        return costReduction;
    }

    public int getLevel() {
        return level;
    }

    public Integer getTypeID() {
        return typeID;
    }
}

package ar.edu.itba.paw.webapp.DTO;

import ar.edu.itba.paw.model.Factory;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class FactoryDTO {

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

    public FactoryDTO(){}

    public FactoryDTO(Factory factory) {
        type = factory.getType().getNameCode();
        amount = factory.getAmount();
        inputReduction = factory.getInputReduction();
        outputMultiplier = factory.getOutputMultiplier();
        costReduction = factory.getCostReduction();
        level = factory.getLevel();
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
}

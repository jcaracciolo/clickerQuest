package ar.edu.itba.paw.webapp.DTO.factories;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.webapp.DTO.map.MapDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseFactoryRecipeDTO {

    public static String url = "factories/%s/recipe";

    @XmlElement(name = "factory_id")
    int factoryId;

    @XmlElement(name = "factory_type")
    String type;

    @XmlElement(name = "recipe")
    MapDTO recipe;

    public BaseFactoryRecipeDTO(){}
    public BaseFactoryRecipeDTO(FactoryType factoryType) {
        factoryId = factoryType.getId();
        type = factoryType.getNameCode();
        recipe = new MapDTO(factoryType.getBaseCost().rawMap(), factoryType.getBaseRecipe().rawMap());
    }


}

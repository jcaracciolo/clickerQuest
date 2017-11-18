package ar.edu.itba.paw.webapp.DTO.users;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.webapp.DTO.map.MapDTO;

import javax.xml.bind.annotation.XmlElement;

public class FactoryRecipeDTO {
    public static String url = "users/%s/factories/%s/recipe";

    @XmlElement(name = "user_id")
    long userId;

    @XmlElement(name = "factory_id")
    int factoryId;

    @XmlElement(name = "recipe")
    MapDTO recipe;

    public FactoryRecipeDTO(){}
    public FactoryRecipeDTO(Factory factory) {
        this.userId = factory.getUserid();
        factoryId = factory.getType().getId();
        recipe = new MapDTO(factory.getCost().rawMap(), factory.getRecipe().rawMap());
    }


}

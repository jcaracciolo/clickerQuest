package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.BaseCost;
import ar.edu.itba.paw.model.packages.Implementations.BaseRecipe;
import ar.edu.itba.paw.model.packages.PackageBuilder;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    STOCK_INVESTMENT_BASE(0,"stock-investment-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.MONEY,1000D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.MONEY,1D);
            return recipeBuilder.buildPackage();
        }},

    PEOPLE_RECRUITING_BASE(1,"people-recruiting-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.MONEY,200D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.PEOPLE,0.3D);
            return recipeBuilder.buildPackage();
        }},

    JUNK_COLLECTOR_BASE(2,"junk-collector-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,5D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.TIRES,0.2D);
            recipeBuilder.putItem(ResourceType.METAL_SCRAP,0.6D);
            recipeBuilder.putItem(ResourceType.CARDBOARD,2D);
            return recipeBuilder.buildPackage();
        }},

    METAL_SEPARATOR_BASE(3,"metal-separator-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,2D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.METAL_SCRAP,-3D);
            recipeBuilder.putItem(ResourceType.IRON,0.6D);
            recipeBuilder.putItem(ResourceType.COPPER,0.4D);
            return recipeBuilder.buildPackage();
        }},

    RUBBER_SHREDDER_BASE(4,"rubber-shredder-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,2D);
            costBuilder.putItem(ResourceType.IRON,50D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.TIRES,-2D);
            recipeBuilder.putItem(ResourceType.RUBBER,0.8D);
            return recipeBuilder.buildPackage();
        }},

    CABLE_MAKER_BASE(5,"cable-maker-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,5D);
            costBuilder.putItem(ResourceType.IRON,250D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.COPPER,-3D);
            recipeBuilder.putItem(ResourceType.RUBBER,-5D);
            recipeBuilder.putItem(ResourceType.COPPER_CABLE,0.8D);
            return recipeBuilder.buildPackage();
        }},

    BOILER_BASE(6,"boiler-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,20D);
            costBuilder.putItem(ResourceType.IRON,500D);
            costBuilder.putItem(ResourceType.COPPER_CABLE,200D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.CARDBOARD,-10D);
            recipeBuilder.putItem(ResourceType.POWER,1D);
            return recipeBuilder.buildPackage();
        }},

    CIRCUIT_MAKER_BASE(7,"circuit-maker-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = BaseCost.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,250D);
            costBuilder.putItem(ResourceType.IRON,1000D);
            costBuilder.putItem(ResourceType.COPPER_CABLE,500D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = BaseRecipe.packageBuilder();
            recipeBuilder.putItem(ResourceType.POWER,-100D);
            recipeBuilder.putItem(ResourceType.COPPER_CABLE,-5D);
            recipeBuilder.putItem(ResourceType.CIRCUITS,2D);
            return recipeBuilder.buildPackage();
        }},
    ;

    private int id;
    private String nameCode;
    FactoryType(int i,String nameCode){
        id=i;
        this.nameCode = nameCode;
    }

    public abstract BaseCost getBaseCost();
    public abstract BaseRecipe getBaseRecipe();

    public Factory defaultFactory(long userId) {
        return new Factory(userId,this,
                0,
                1,1,1,
                0);
    }

    public String getNameCode(){
        return nameCode;
    }
    public int getId() {
        return id;
    }

    public static FactoryType fromId(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }

}

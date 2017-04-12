package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.BaseCost;
import ar.edu.itba.paw.model.packages.Implementations.BaseRecipe;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.PackageType;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    STOCK_INVESTMENT_BASE(0,"stock-investment-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.MONEY,1000D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.MONEY,1D);
            return recipeBuilder.buildPackage();
        }},

    PEOPLE_RECRUITING_BASE(1,"people-recruiting-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.MONEY,200D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.PEOPLE,0.3D);
            return recipeBuilder.buildPackage();
        }},

    JUNK_COLLECTOR_BASE(2,"junk-collector-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,5D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.TIRES,0.2D);
            recipeBuilder.putItem(ResourceType.METAL_SCRAP,0.6D);
            recipeBuilder.putItem(ResourceType.CARDBOARD,2D);
            return recipeBuilder.buildPackage();
        }},

    METAL_SEPARATOR_BASE(3,"metal-separator-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,2D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.METAL_SCRAP,-3D);
            recipeBuilder.putItem(ResourceType.IRON,0.6D);
            recipeBuilder.putItem(ResourceType.COPPER,0.4D);
            return recipeBuilder.buildPackage();
        }},

    RUBBER_SHREDDER_BASE(4,"rubber-shredder-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,2D);
            costBuilder.putItem(ResourceType.IRON,50D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.TIRES,-2D);
            recipeBuilder.putItem(ResourceType.RUBBER,0.8D);
            return recipeBuilder.buildPackage();
        }},

    CABLE_MAKER_BASE(5,"cable-maker-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,5D);
            costBuilder.putItem(ResourceType.IRON,250D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.COPPER,-3D);
            recipeBuilder.putItem(ResourceType.RUBBER,-5D);
            recipeBuilder.putItem(ResourceType.COPPER_CABLE,0.8D);
            return recipeBuilder.buildPackage();
        }},

    BOILER_BASE(6,"boiler-base"){
        public BaseCost getBaseCost(){
            PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
            costBuilder.putItem(ResourceType.PEOPLE,20D);
            costBuilder.putItem(ResourceType.IRON,500D);
            costBuilder.putItem(ResourceType.COPPER_CABLE,200D);
            return costBuilder.buildPackage();
        }
        public BaseRecipe getBaseRecipe() {
            PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
            recipeBuilder.putItem(ResourceType.CARDBOARD,-10D);
            recipeBuilder.putItem(ResourceType.POWER,1D);
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

//    public BaseCost getBaseCost(){
//        PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();
//
//        switch (this) {
//            case METAL_OVEN_BASE:
//                costBuilder.putItem(ResourceType.POWER,10D);
//                break;
//            case METAL_SEPARATOR_BASE:
//                costBuilder.putItem(ResourceType.POWER,100D);
//                break;
//            case STOCK_INVESTMENT_BASE:
//                costBuilder.putItem(ResourceType.POWER,100D);
//                costBuilder.putItem(ResourceType.PLASTIC,100D);
//                break;
//            case JUNK_COLLECTOR_BASE:
//                costBuilder.putItem(ResourceType.PLASTIC,100D);
//                costBuilder.putItem(ResourceType.POWER,100D);
//                costBuilder.putItem(ResourceType.GOLD,1000D)    ;
//                break;
//            default:
//                throw new RuntimeException("There is no cost for this factory");
//        }
//
//        return costBuilder.buildPackage();
//    }
//
//    public BaseRecipe getBaseRecipe() {
//        PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();
//
//        switch (this) {
//            case NOTHINGFORWOOD:
//                recipeBuilder.putItem(ResourceType.POWER, 3D);
//                break;
//            case METAL_SEPARATOR_BASE:
//                recipeBuilder.putItem(ResourceType.POWER, -3D);
//                recipeBuilder.putItem(ResourceType.PLASTIC, 1D);
//                break;
//            case STOCK_INVESTMENT_BASE:
//                recipeBuilder.putItem(ResourceType.PLASTIC, -2D);
//                recipeBuilder.putItem(ResourceType.GOLD, 1D);
//                break;
//            case JUNK_COLLECTOR_BASE:
//                recipeBuilder.putItem(ResourceType.POWER, -10D);
//                recipeBuilder.putItem(ResourceType.GOLD, 1D);
//                break;
//            default:
//                throw new RuntimeException("There is no ResourcePackage for this factory");
//        }
//        return recipeBuilder.buildPackage();
//    }
}

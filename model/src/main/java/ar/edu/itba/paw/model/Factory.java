package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "factories")
@IdClass(Factory.FactoryID.class)
public class Factory implements Comparable<Factory> {

    @Id
    @Column(nullable = false)
    private long userid;

    @Id
    @Column(name = "type", nullable = false)
    private int _type;

    @Transient
    private FactoryType type;

    @Column(name = "amount")
    private double amount;

    @Column(name = "inputreduction")
    private double inputReduction;

    @Column(name = "outputmultiplier")
    private double outputMultiplier;

    @Column(name = "costreduction")
    private double costReduction;

    @Column(name = "level")
    private int level;

    @PostLoad
    private void postLoad(){
        type = FactoryType.fromId(_type);
    }

    public Factory(){}

    static class FactoryID implements Serializable {
        @Column(nullable = false)
        private long userid;
        @Column(name = "type", nullable = false)
        private int _type;
        FactoryID(){};
        FactoryID(long userid,int _type){
            this.userid = userid;
            this._type = _type;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FactoryID that = (FactoryID) o;

            if (getUserid() != that.getUserid()) return false;
            return getType() == that.getType()  ;

        }
        @Override
        public int hashCode() {
            int result = (int) (getUserid() ^ (getUserid() >>> 32));
            result = 31 * result + getType();
            return result;
        }

        public long getUserid() {
            return userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public int getType() {
            return _type;
        }

        public void setType(int type) {
            this._type = type;
        }
    }

    public Factory(long userid, @NotNull FactoryType type, double amount, double inputReduction,
                   double outputMultiplier, double costReduction, int level) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.level = level;
        this._type = type.getId();
    }

    public long getUserid() {
        return userid;
    }

    public FactoryType getType() {
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

    public Recipe getRecipe() {
        return  type.getBaseRecipe()
                .calculateRecipe(inputReduction,outputMultiplier,level);
    }

    public FactoryCost getCost() {
        return getCost(1);
    }
    public FactoryCost getCost(long amountToBuy) {
        return type.getBaseCost().calculateCost(amount,costReduction,amountToBuy);
    }

    public FactoriesProduction getFactoriesProduction() {
        return type.getBaseRecipe().calculateProduction(amount,inputReduction,outputMultiplier,level);
    }

    public String getImage(){
        return getType().getId() + ".jpg";
    }

    public Factory purchaseResult(long amountToBuy) {
        return new Factory(userid,type,
                amount +amountToBuy,
                inputReduction,outputMultiplier,costReduction,level);
    }

    public Factory upgradeResult(){
        Upgrade newUpgrade = getNextUpgrade();

        return new Factory(userid,type,amount,
                            newUpgrade.getInputReduction(),
                            newUpgrade.getOutputMultiplier(),
                            newUpgrade.getCostReduction(),
                            level + 1
        );
    }


    public boolean isBuyable(Wealth w, long amountToBuy) {
        FactoryCost cost = getCost(amountToBuy);
        Storage storage = w.getStorage();

        for (ResourceType r: cost.getResources()) {
            if(cost.getValue(r) > storage.getValue(r)){
                return false;
            }
        }

        Map<ResourceType,Double> need = type.getBaseRecipe().getInputs();
        Productions productions = w.getProductions();

        for (ResourceType r: need.keySet()) {
            if(need.get(r)*amountToBuy > productions.getValue(r)){
                return false;
            }
        }

        return true;
    }

    public BuyLimits getLimits(Wealth w){
        BuyLimits buyLimits = new BuyLimits(this);


        Map<ResourceType,Double> need = type.getBaseRecipe().getInputs();
        Productions productions = w.getProductions();
        for (ResourceType r:need.keySet()){
            Double limit = productions.getValue(r)/need.get(r);
            buyLimits.addProductionDeficit(r,limit);
        }

        Storage storage = w.getStorage();
        for (ResourceType r:type.getBaseCost().getResources()){
            Double a = 1D;
            Double b = 1+2*getAmount();
            Double c = -2*(storage.getValue(r))/(type.getBaseCost().getValue(r)*getCostReduction());
            Double sol=0D;
            double sol1 = (-b + Math.sqrt(b*b - 4*a*c) ) / (2*a);
            double sol2 = (-b - Math.sqrt(b*b - 4*a*c) ) / (2*a);
            sol = sol1<sol2?sol2:sol1;
            buyLimits.addStorageDeficit(r,sol);
        }
        return buyLimits;
    }

    public Upgrade getNextUpgrade() {
        return Upgrade.getUpgrade(getType(),getLevel() + 1);
    }

    public int compareTo(Factory f) {
        return this.getType().getId() - f.getType().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Factory factory = (Factory) o;

        if (userid != factory.userid) return false;
        return type == factory.type;

    }

    @Override
    public int hashCode() {
        int result = (int) (userid ^ (userid >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public int getLevel() {
        return level;
    }

    public boolean isUpgreadable(Wealth w) {
        if(getAmount()>0) {
            return getNextUpgrade().isBuyable(w);
        }

        return false;
    }

}

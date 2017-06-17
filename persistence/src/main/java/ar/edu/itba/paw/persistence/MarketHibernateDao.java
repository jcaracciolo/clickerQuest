package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.StockMarketEntry;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by juanfra on 17/06/17.
 */
@Repository
public class MarketHibernateDao implements MarketDao {

    @PersistenceContext
    private EntityManager em;

    private StockMarketEntry findByResource(ResourceType type){
        final TypedQuery<StockMarketEntry> query = em.createQuery( "from StockMarketEntry as s where s._resourcetype = :resource" ,
                StockMarketEntry.class);
        query.setParameter("resource",type.getId());
        List<StockMarketEntry> list = query.getResultList();
        return list.isEmpty()?null:list.get(0);    }

    @Override
    public boolean registerPurchase(StockMarketEntry stockMarketEntry) {
        StockMarketEntry sme = findByResource(stockMarketEntry.getResourceType());
        if(sme == null){
            em.persist(stockMarketEntry);
        } else {
            sme.setAmount(sme.getAmount() + stockMarketEntry.getAmount());
            em.merge(sme);
        }
        return true;
    }

    @Override
    public Map<ResourceType, Double> getPopularities() {
        final TypedQuery<StockMarketEntry> query = em.createQuery( "from StockMarketEntry as s" ,
                StockMarketEntry.class);

        List<StockMarketEntry> entries = query.getResultList();
        Map<ResourceType,Double> popularities = new HashMap<>();
        if(entries.isEmpty()) return Collections.emptyMap();

        Double minRes = entries.stream()
                .map(StockMarketEntry::getAmount)
                .reduce((d1,d2)->d1<d2?d1:d2)
                .orElse(null);

        Double min = minRes<0?-minRes:1;

        Double total = 0D;
        for(ResourceType r: ResourceType.values()) total+=min;

        total += entries.stream()
                .map(StockMarketEntry::getAmount)
                .reduce((d1,d2)-> d1+d2).orElse(null);

        Double finalTotal = total;
        entries.forEach( (e) -> {
                    double value = e.getAmount() + min;
                    double popularity = popularityCalculator(value,ResourceType.values().length,finalTotal);
                    if(popularity<0.5) popularity=0.5;
                    popularities.put(e.getResourceType(),popularity);
                }
        );
        Stream.of(ResourceType.values()).filter((r)->!popularities.containsKey(r)).forEach((r)->popularities.put(r,min));
        return popularities;
    }

    public double popularityCalculator(double purchases, double totalAmount, double totalSum) {
        return popularityExponential(purchases * totalAmount / totalSum);
    }

    private double popularityExponential(double x) {
        double slope = 0.3;
        double max = 3D;
        return ( (1+1/max) - Math.exp(-(x-1) * slope) ) * max;
    }

}

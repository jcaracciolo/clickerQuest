package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.interfaces.MarketService;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.clan.Clan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by juanfra on 17/05/17.
 */
@Service
@Lazy
public class MarketServiceImpl implements MarketService {
    private static final long refreshTime = 2*60*1000;

    @Autowired
    MarketDao marketDao;

    @Scheduled(fixedDelay=refreshTime)
    @Transactional(propagation = Propagation.NESTED)
    public void updatePrices(){
        Map<ResourceType,Double> popularities = marketDao.getPopularities();
        popularities.forEach(ResourceType::setPopularity);
    }
}
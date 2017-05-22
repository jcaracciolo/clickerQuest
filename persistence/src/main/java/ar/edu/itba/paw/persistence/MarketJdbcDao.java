package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.StockMarketEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MarketJdbcDao implements MarketDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertStockMakert;


    private final static RowMapper<StockMarketEntry> MARKET_STOCK_ROW_MAPPER = (rs, rowNum) ->
                    new StockMarketEntry(
                            ResourceType.fromId(rs.getInt("resourceType")),
                            rs.getDouble("amount"));


    private final static ReverseRowMapper<StockMarketEntry> MARKET_REVERSE_STOCK_ROW_MAPPER = (sme) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("resourceType",    sme.getResourceType().getId());
        args.put("amount",          sme.getAmount());
        return args;
    };

    @Autowired
    public MarketJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

        jdbcInsertStockMakert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("stockMarket");
    }

    @Override
    public boolean registerPurchase(StockMarketEntry stockMarketEntry) {
        try {
            List<StockMarketEntry> entryList = jdbcTemplate.query(
                    "SELECT * from stockMarket where resourceType = ?",
                    MARKET_STOCK_ROW_MAPPER,stockMarketEntry.getResourceType().getId());

            if(entryList.size()>1) {
                //TODO log this
                throw new RuntimeException("Database corrupt");
            }
            StockMarketEntry last  =
                    entryList.size() == 0?
                            new StockMarketEntry(stockMarketEntry.getResourceType(),0):
                            entryList.get(0);

            StockMarketEntry update = new StockMarketEntry(
                    last.getResourceType(),
                    last.getAmount() + stockMarketEntry.getAmount()>0? 1 : -1
                    );

            if(entryList.size() == 0) {
                jdbcInsertStockMakert.execute(MARKET_REVERSE_STOCK_ROW_MAPPER.toArgs(update));
            } else {
                jdbcTemplate.query(
                        "UPDATE stockMarket SET amount = ?" +
                                " where resourceType = ?",
                        MARKET_STOCK_ROW_MAPPER,
                        update.getAmount(),
                        update.getResourceType().getId());
            }
        }catch (Exception e) {
            return false;
        }

        return true;
    }

    @Scheduled(fixedDelay=100000)
    public void updatePrices(){
        List<StockMarketEntry> entries = jdbcTemplate.query("SELECT * FROM stockMarket",MARKET_STOCK_ROW_MAPPER);
        Double total = entries.stream().map(StockMarketEntry::getAmount).reduce((d1,d2)-> d1+d2).orElse(null);
        if(total==null) return;
        entries.forEach(
                (e) -> {
                    double popularity = popularityCalculator(e.getAmount(),entries.size(),total);
                    e.getResourceType().setPopularity(popularity);
                }
        );
    }

    public double popularityCalculator(double puchases, double totalAmount, double totalSum) {
        return popularityExponential(puchases * totalAmount / totalSum);
    }

    private double popularityExponential(double x) {
        double slope = 0.5;
        double max = 3D;

        return ( (1+1/max) - Math.exp(-(x-1) * slope) ) * max;
    }

}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.StockMarketEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanfra on 09/05/17.
 */
@Repository
public class MarketJdbcDao implements MarketDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertStockMakert;


    private final static RowMapper<StockMarketEntry> MARKET_STOCK_ROW_MAPPER = (rs, rowNum) ->
            new StockMarketEntry(rs.getLong("time"),
                    rs.getLong("userid"),
                    ResourceType.fromId(rs.getInt("resourceType")),
                    rs.getDouble("amount"));


    private final static ReverseRowMapper<StockMarketEntry> MARKET_REVERSE_STOCK_ROW_MAPPER = (sme) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("time",            sme.getTime());
        args.put("userid",          sme.getUserid());
        args.put("resourceType",    sme.getResourceType());
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
            jdbcInsertStockMakert.execute(MARKET_REVERSE_STOCK_ROW_MAPPER.toArgs(stockMarketEntry));
        }catch (Exception e) {
            return false;
        }

        return true;
    }

    private static Calendar toCalendar(long milis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milis);
        return cal;
    }
}

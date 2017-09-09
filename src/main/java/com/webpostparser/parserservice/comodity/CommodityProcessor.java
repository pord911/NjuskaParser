package com.webpostparser.parserservice.comodity;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Domagoj Pordan on 27.08.17..
 */
@Component
public class CommodityProcessor {
    private List<Commodity> inserts = new LinkedList<Commodity>();
    private List<Commodity> updates = new LinkedList<Commodity>();
    private List<Commodity> deletes = new LinkedList<Commodity>();
    private Map<Commodity, Commodity> commodities;

    public void setCommodities(Map<Commodity, Commodity> commodities) {
        this.commodities = commodities;
    }

    public void processCommodities(List<? extends Commodity> fetchedCommodities) {
        Commodity element;
        for (Commodity commodity : fetchedCommodities) {
            if ((element = commodities.get(commodity)) != null) {
                if (element.equals(commodity))
                    commodities.remove(element);
                else
                    updates.add(commodity);
            } else {
                inserts.add(commodity);
            }
        }
        if (!commodities.isEmpty()) {
            Set<Commodity> keys = commodities.keySet();
            for (Commodity key : keys) {
                deletes.add(commodities.get(key));
            }
        }
    }

    public List<Commodity> getInserts() {
        return inserts;
    }

    public List<Commodity> getUpdates() {
        return updates;
    }

    public List<Commodity> getDeletes() {
        return deletes;
    }
}

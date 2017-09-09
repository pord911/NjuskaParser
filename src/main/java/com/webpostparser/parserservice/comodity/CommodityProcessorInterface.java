package com.webpostparser.parserservice.comodity;

import java.util.List;
import java.util.Map;

/**
 * Created by Domagoj Pordan on 27.08.17..
 */
public interface CommodityProcessorInterface<T> {
    public void processCommodities(List<T> fetchedCommodities);
    public void setCommodities(Map<T, T> commodities);
    public List<T> getInserts();
    public List<T> getUpdates();
    public List<T> getDeletes();
}

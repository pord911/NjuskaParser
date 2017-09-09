package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.CommodityProcessor;
import com.webpostparser.parserservice.comodity.ComodityType;

import java.io.IOException;

/**
 * Created by pord on 08.07.17..
 */
public interface Parser {
    public void processPosts(CommodityProcessor commodityProcessor, ComodityType comodityType, String city) throws IOException;
}

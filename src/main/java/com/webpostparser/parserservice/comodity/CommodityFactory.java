package com.webpostparser.parserservice.comodity;

import com.webpostparser.parserservice.parsehelpers.LinkHashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Domagoj Pordan on 23.08.17..
 */
@Configuration
public class CommodityFactory {
    @Autowired
    LinkHashHelper linkHashHelper;

    public Property createProperty(String link) {
        Property property = new Property(link, linkHashHelper);
        property.init();
        return property;
    }

    public  CommodityProcessor createCommodityProcessor(ComodityType type) {
        CommodityProcessor commodityProcessor = null;
        switch (type) {
            case HOUSE:
            case FLAT:
                commodityProcessor = new CommodityProcessor();
            default:
                //TODO: Throw InvalidCommodityException
        }
        return commodityProcessor;
    }
}
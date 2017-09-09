package com.webpostparser.processor;

import com.webpostparser.model.DaoFactory;
import com.webpostparser.model.DaoCommoditynterface;
import com.webpostparser.parserservice.comodity.*;
import com.webpostparser.parserservice.parsers.Parser;
import com.webpostparser.parserservice.parsers.ParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Domagoj Pordan on 23.06.17..
 */
@Component
public class PostProcessor {
    List<String> cityList;
    ParserFactory parserFactory;
    DaoFactory daoFactory;

    @Autowired
    private CommodityFactory commodityFactory;
    @Autowired
    private CommodityProcessor commodityProcessor;

    public PostProcessor() {
        this.cityList = new LinkedList<String>();
        initCityList();
    }

    @Autowired
    public void setParserFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    @Autowired
    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void initCityList() {
        cityList.add("zagreb");
    }

    public void processPosts(ComodityType type) throws IOException, NoSuchAlgorithmException {

        DaoCommoditynterface dao = daoFactory.createDao(type);
        Parser parser = parserFactory.createParser(type);

        Map<Commodity, Commodity> linkHashes = dao.getHashValues();

        commodityProcessor.setCommodities(linkHashes);
        for (String city : cityList) {
            parser.processPosts(commodityProcessor, type, city);
            //dao.delete(commodityProcessor.getDeletes());
            dao.insertList(commodityProcessor.getInserts());
            //dao.update(commodityProcessor.getUpdates());
        }
    }
}

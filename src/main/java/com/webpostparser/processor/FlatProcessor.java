package com.webpostparser.processor;

import com.webpostparser.model.FlatsDao;
import com.webpostparser.parserservice.comodity.Config;
import com.webpostparser.parserservice.comodity.FlatConfig;
import com.webpostparser.parserservice.parsers.FlatParser;
import com.webpostparser.parserservice.posts.Flat;
import com.webpostparser.parserservice.posts.FlatList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Domagoj Pordan on 23.06.17..
 */
@Component
public class FlatProcessor {
    List<String> cityList;
    FlatParser flatParser;
    FlatsDao flatsDao;

    public FlatProcessor() {
        this.cityList = new LinkedList<String>();
        initCityList();
    }

    @Autowired
    public void setFlatParser(FlatParser flatParser) {
        this.flatParser = flatParser;
    }

    @Autowired
    public void setFlatsDao(FlatsDao flatsDao) {
        this.flatsDao = flatsDao;
    }

    public void initCityList() {
        cityList.add("zagreb");
    }

    public void processFlats() throws IOException, NoSuchAlgorithmException {
        FlatList list;
        HashSet<String> linkHashes = flatsDao.getHashValues();
        for (String city : cityList) {
            list = flatParser.getPosts(new FlatConfig(city), linkHashes);
        }
    }
}

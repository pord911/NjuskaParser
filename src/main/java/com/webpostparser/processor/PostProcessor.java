package com.webpostparser.processor;

import com.webpostparser.model.DaoFactory;
import com.webpostparser.model.DaoFactoryInterface;
import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.parsers.Parser;
import com.webpostparser.parserservice.parsers.ParserFactory;
import com.webpostparser.parserservice.posts.CommodityList;
import com.webpostparser.parserservice.posts.CommodityPost;
import com.webpostparser.parserservice.posts.Flat;
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
public class PostProcessor {
    List<String> cityList;
    ParserFactory parserFactory;
    DaoFactory daoFactory;

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
        CommodityList list;
        DaoFactoryInterface dao = daoFactory.createDao(type);
        Parser parser = parserFactory.getParser(type);

        HashSet<String> linkHashes = dao.getHashValues();
        for (String city : cityList) {
            list = parser.getPosts(city, type, linkHashes);

            dao.insertList(list.getList());
        }

        for (CommodityPost post : dao.getList().getList()) {
            Flat flat = (Flat)post.getObject();
            System.out.println("City: " + flat.getCity());
            System.out.println("Title: " + flat.getTitle());
            System.out.println("Area: " + flat.getArea());
            System.out.println("Price: " + flat.getPrice());
            System.out.println("Date: " + flat.getDate());
            System.out.println("Link: " + flat.getLink());
            System.out.println("Image link: " + flat.getImageLink());
            System.out.println("Hash: " + flat.getLinkHash());
            System.out.println("Type: " + flat.getFlatType());
        }
    }
}

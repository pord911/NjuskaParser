package com.webpostparser.parserservice.parsers;

import com.webpostparser.parserservice.comodity.ComodityType;
import com.webpostparser.parserservice.posts.CommodityList;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

/**
 * Created by pord on 08.07.17..
 */
public interface Parser {
    public CommodityList getPosts(String city, ComodityType type, HashSet<String> linkHashSet)
            throws IOException, NoSuchAlgorithmException;
}

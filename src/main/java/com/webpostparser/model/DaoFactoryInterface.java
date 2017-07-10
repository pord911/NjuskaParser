package com.webpostparser.model;

import com.webpostparser.parserservice.posts.CommodityList;
import com.webpostparser.parserservice.posts.CommodityPost;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by pord on 08.07.17..
 */
public interface DaoFactoryInterface {
    public CommodityList getList();
    public boolean insertList(Collection<CommodityPost> flatList);
    public HashSet<String> getHashValues();

}

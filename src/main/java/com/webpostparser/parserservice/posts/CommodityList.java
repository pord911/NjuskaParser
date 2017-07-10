package com.webpostparser.parserservice.posts;

import java.util.Collection;

/**
 * Created by Domagoj Pordan on 08.07.17..
 */
public interface CommodityList {
    public void add(CommodityPost post);
    public Collection<CommodityPost> getList();
    public boolean isListEmpty();
}

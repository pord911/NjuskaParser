package com.webpostparser.parserservice.posts;

import java.util.*;

/**
 * Created by Domagoj Pordan on 11.06.17..
 */
public class FlatList implements CommodityList {
    HashMap<String, CommodityPost> flatList;
    public FlatList() {
        flatList = new HashMap<String, CommodityPost>();
    }

    public void add(CommodityPost post) {
        Flat flatPost = (Flat)post.getObject();
        if (flatPost.getLinkHash() == null) {
            System.out.println("Cannot store post element, no link hash available.");
            return;
        }
        if (flatList.put(flatPost.getLinkHash(), post) != null)
            System.out.println("Skipping duplicate element, title: " + flatPost.getTitle() +
                   ", link: " + flatPost.getLink() + ", link hash: " + flatPost.getLinkHash());
    }

    public Collection<CommodityPost> getList() {
        return flatList.values();
    }

    public boolean isListEmpty() {
        return flatList.isEmpty();
    }
}

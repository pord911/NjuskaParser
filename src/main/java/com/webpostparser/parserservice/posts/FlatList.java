package com.webpostparser.parserservice.posts;

import java.util.*;

/**
 * Created by pord on 11.06.17..
 */
public class FlatList {
    HashMap<String, Flat> flatList;
    public FlatList() {
        flatList = new HashMap<String, Flat>();
    }

    public void add(Flat post) {
        if (post.getLinkHash() == null) {
            System.out.println("Cannot store post element, no link hash available.");
            return;
        }
        if (flatList.put(post.getLinkHash(), post) != null)
            System.out.println("Skipping duplicate element, title: " + post.getTitle() +
                   ", link: " + post.getLink() + ", link hash: " + post.getLinkHash());
    }

    public Collection<Flat> getList() {
        return flatList.values();
    }

    public boolean isListEmpty() {
        return flatList.isEmpty();
    }
}

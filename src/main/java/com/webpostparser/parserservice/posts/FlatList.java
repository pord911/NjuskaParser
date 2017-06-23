package com.webpostparser.parserservice.posts;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pord on 11.06.17..
 */
public class FlatList {
    LinkedList<Flat> flatList;

    public FlatList() {
        flatList = new LinkedList<Flat>();
    }

    public void add(Flat post) {
        flatList.add(post);
    }

    public List<Flat> getList() {
        return flatList;
    }

    public boolean isListEmpty() {
        return flatList.isEmpty();
    }

    public Flat getLastElement() {
        return flatList.getLast();
    }
}

package com.webparser.posts;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pord on 11.06.17..
 */
public class FlatList {
    List<FlatPost> flatList;

    public FlatList() {
        flatList = new LinkedList<FlatPost>();
    }

    public void add(FlatPost post) {
        flatList.add(post);
    }

    public List<FlatPost> getList() {
        return flatList;
    }

    public boolean isListEmpty() {
        return flatList.isEmpty();
    }
}

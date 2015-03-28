package com.shimika.dfa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Administrator on 2015-03-28.
 */
public class NFAGraph {
    ArrayList<ArrayList<Edge>> lists;

    public NFAGraph(int size) {
        lists = new ArrayList<ArrayList<Edge>>();

        for(int i = 0; i <= size; i++) {
            lists.add(new ArrayList<Edge>());
        }
    }

    public void add(int start, int end, int value) {
        lists.get(start).add(new Edge(end, value));
    }

    public ArrayList<Edge> getList(int start) {
        return lists.get(start);
    }
}

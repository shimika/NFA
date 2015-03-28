package com.shimika.dfa;

/**
 * Created by Administrator on 2015-03-28.
 */
public class Edge {
    int nextNode;
    int value;

    public Edge(int n, int v) {
        this.nextNode = n;
        this.value = v;
    }

    public int getNextNode() {
        return this.nextNode;
    }

    public int getValue() {
        return this.value;
    }
}

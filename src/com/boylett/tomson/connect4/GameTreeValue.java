package com.boylett.tomson.connect4;

/**
 * Created by Tomson on 16/05/2016.
 */
public class GameTreeValue {
    private int heuristic;
    private int[] point;

    public GameTreeValue(int heuristic, int[] point) {
        this.heuristic = heuristic;
        this.point = point;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int[] getPoint() {
        return point;
    }
}

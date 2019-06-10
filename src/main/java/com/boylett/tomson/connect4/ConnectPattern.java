package com.boylett.tomson.connect4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tomson on 12/05/2016.
 */

public class ConnectPattern {
    public static final char BLANK = '_';
    public static final char RED = 'r';
    public static final char YELLOW = 'y';

    private static final int NUMBER_IN_ROW = ConnectGame.NUMBER_IN_ROW;
    private static final int[][] DIRECTIONS = {{1,0},{1,1},{0,1},{-1,1}};

    private ConnectPattern() {
        // Static methods only.
    }

    public static int[][] isFourInRowDetail(char[][] boardData) {
        // For each space in the board...
        for (int i = 0; i < boardData.length; i++) {
            for (int j = 0; j < boardData[i].length; j++) {

                // If a space has red or yellow in it...
                if (boardData[i][j] != BLANK) {

                    // Check in 4 directions to find 4 in a row.
                    for (int[] DIRECTION : DIRECTIONS) {
                        if (isFourIn(boardData, i, j, DIRECTION[0], DIRECTION[1], (boardData[i][j] == RED))) {
                            int[][] points = new int[NUMBER_IN_ROW][2];
                            for (int k = 0; k < NUMBER_IN_ROW; k++) {
                                points[k][0] = i + (k * DIRECTION[0]);
                                points[k][1] = j + (k * DIRECTION[1]);
                            }
                            return points;
                        }
                    }

                }

            }
        }
        return new int[][]{};
    }

    public static boolean isFourInRow(char[][] boardData) {
        return (isFourInRowDetail(boardData).length > 0);
    }

    private static boolean isFourIn(char[][] boardData, int startX, int startY, int offsetX, int offsetY, boolean turn) {
        char side = ConnectGame.iconToChar(turn ? GameBoardSpace.RED : GameBoardSpace.YELLOW);

        if (((startX + (NUMBER_IN_ROW - 1)*offsetX) >= boardData.length) ||
                ((startX + (NUMBER_IN_ROW - 1)*offsetX) < 0) ||
                ((startY + (NUMBER_IN_ROW - 1)*offsetY) >= boardData[startX].length) ||
                ((startY + (NUMBER_IN_ROW - 1)*offsetY) < 0)) {
            return false;
        }

        for (int i = 0; i <= (NUMBER_IN_ROW - 1); i++) {
            if (boardData[startX + (i * offsetX)][startY + (i * offsetY)] != side) {
                return false;
            }
        }

        return true;
    }

    public static int[][] getPossibleMoves(char[][] boardData) {
        List<int[]> possibleMoves = new ArrayList<>();
        for (int i = 0; i < boardData.length; i++) {
            for (int j = (boardData[i].length - 1); j >= 0 ; j--) {
                if (boardData[i][j] == BLANK) {
                    possibleMoves.add(new int[]{i,j});
                    break;
                }
            }
        }
        return possibleMoves.toArray(new int[possibleMoves.size()][2]);
    }

    public static int getHeuristic(char[][] boardData, boolean turn) {
        char[][] bdCopy = new char[boardData.length][boardData[0].length];
        for (int i = 0; i < boardData.length; i++) {
            bdCopy[i] = Arrays.copyOf(boardData[i], boardData[i].length);
        }

        int heuristic = 0;
        // For each space in the board...
        for (int i = 0; i < bdCopy.length; i++) {
            for (int j = 0; j < bdCopy[i].length; j++) {
                if (bdCopy[i][j] == BLANK) {
                    bdCopy[i][j] = RED;

                    if (isFourInRow(bdCopy)) {
                        heuristic++;
                    }
                    else {
                        bdCopy[i][j] = YELLOW;

                        if(isFourInRow(bdCopy)) {
                            heuristic--;
                        }
                    }
                    bdCopy[i][j] = BLANK;
                }

            }
        }

        return heuristic;
    }
}

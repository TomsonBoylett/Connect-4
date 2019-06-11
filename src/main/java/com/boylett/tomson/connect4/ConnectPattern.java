package com.boylett.tomson.connect4;

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
                        if (isFourIn(boardData, i, j, DIRECTION[0], DIRECTION[1])) {
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

    private static boolean isFourIn(char[][] boardData, int startX, int startY, int directionX, int directionY) {
        char side = boardData[startX][startY];
        
        if (side == BLANK) {
            return false;
        }

        if  (((startX + (NUMBER_IN_ROW - 1)*directionX) >= boardData.length) ||
             ((startX + (NUMBER_IN_ROW - 1)*directionX) < 0) ||
             ((startY + (NUMBER_IN_ROW - 1)*directionY) >= boardData[0].length) ||
             ((startY + (NUMBER_IN_ROW - 1)*directionY) < 0)) {
            return false;
        }

        for (int i = 0; i < NUMBER_IN_ROW; i++) {
            if (boardData[startX + (i * directionX)][startY + (i * directionY)] != side) {
                return false;
            }
        }

        return true;
    }
}

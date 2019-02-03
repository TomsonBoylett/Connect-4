package com.boylett.tomson.connect4;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Tomson on 12/05/2016.
 */
public class ConnectPatternTest {
    @org.junit.Test
    // Tests algorithm for all 69 possible win combinations.
    public void isFourInRow() throws Exception {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (!((i + 3*1) >= 7 || (i + 3*1) < 0 || (j + 3*0) >= 6 || (j + 3*0) < 0)) {
                    assertTrue(ConnectPattern.isFourInRow(generateBoardData(7, 6, i, j, 1, 0)));
                }
                if (!((i + 3*1) >= 7 || (i + 3*1) < 0 || (j + 3*1) >= 6 || (j + 3*1) < 0)) {
                    assertTrue(ConnectPattern.isFourInRow(generateBoardData(7, 6, i, j, 1, 1)));
                }
                if (!((i + 3*0) >= 7 || (i + 3*0) < 0 || (j + 3*1) >= 6 || (j + 3*1) < 0)) {
                    assertTrue(ConnectPattern.isFourInRow(generateBoardData(7, 6, i, j, 0, 1)));
                }
                if (!((i + 3*-1) >= 7 || (i + 3*-1) < 0 || (j + 3*1) >= 6 || (j + 3*1) < 0)) {
                    assertTrue(ConnectPattern.isFourInRow(generateBoardData(7, 6, i, j, -1, 1)));
                }
            }
        }
    }

    private char[][] generateBoardData(int columns, int rows, int x, int y, int offsetX, int offsetY) {
        char[][] boardData = new char[columns][rows];
        System.out.println(columns + " " + rows + " " + x + " " + y + " " + offsetX + " " + offsetY);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                boardData[i][j] = '_';
            }
        }
        for (int i = 0; i < 4; i++) {
            boardData[x + i*offsetX][y + i*offsetY] = 'r';
        }

        return boardData;
    }
}
package com.boylett.tomson.connect4;

/**
 * Contains static methods for finding winning rows on the game board.
 * 
 * @author Tomson Boylett
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

    /**
     * Searches the array of characters for a row of length NUMBER_IN_ROW.
     * The row can be in any of the 8 cardinal directions and contains only
     * one type of character. The character must also not be BLANK.
     * 
     * @param board
     * @return The coordinates of each location in the row or an empty array
     * if no row matching the requirements is found.
     * 
     * @see NUMBER_IN_ROW
     * @see BLANK
     */
    public static int[][] isFourInRowDetail(char[][] board) {
        // For each space in the board...
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                // If a space has red or yellow in it...
                if (board[i][j] != BLANK) {

                    // Check in 4 directions for a row
                    for (int[] DIRECTION : DIRECTIONS) {
                        if (isFourIn(board, i, j, DIRECTION[0], DIRECTION[1])) {
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

    /**
     * An alternative version of isFourInRowDetail which instead returns a
     * Boolean.
     * 
     * @param board
     * @return True if isFourInRowDetail returns a non-empty array and false
     * otherwise.
     * 
     * @see isFourInRowDetail
     */
    public static boolean isFourInRow(char[][] board) {
        return (isFourInRowDetail(board).length > 0);
    }

    /**
     * Checks a row in the board data for the requirements stated in
     * isFourInRowDetail
     * 
     * @param board
     * @param startX
     * @param startY
     * @param directionX
     * @param directionY
     * 
     * @return True if matching row and false otherwise
     * 
     * @see isFourInRowDetail
     */
    private static boolean isFourIn(char[][] board, int startX, int startY, int directionX, int directionY) {
        char start = board[startX][startY];

        // Check that row lies within the bounds of the board
        if  (((startX + (NUMBER_IN_ROW - 1)*directionX) >= board.length) ||
             ((startX + (NUMBER_IN_ROW - 1)*directionX) < 0) ||
             ((startY + (NUMBER_IN_ROW - 1)*directionY) >= board[0].length) ||
             ((startY + (NUMBER_IN_ROW - 1)*directionY) < 0)) {
            return false;
        }

        /* Check that the other characters in the row match the
         * starting character.
         */
        for (int i = 1; i < NUMBER_IN_ROW; i++) {
            int x = startX + (i * directionX);
            int y = startY + (i * directionY);
            if (board[x][y] != start) {
                return false;
            }
        }

        return true;
    }
}

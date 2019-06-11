package com.boylett.tomson.connect4;

import javax.swing.*;
import java.awt.event.*;
import java.security.InvalidParameterException;

/**
 * Displays a game of connect 4 in a window.
 * 
 * @author Tomson Boylett
 */
public class ConnectGame {
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    /**
     * Number of tiles in a row to register a victory
     */
    protected static final int NUMBER_IN_ROW = 4;

    /**
     * Current active player in this game
     * 
     * @see getTurnIcon
     */
    private boolean turn;
    private final JPanel[] columns;

    public ConnectGame() {
        columns = new JPanel[COLUMNS];
        turn = true;
    }

    /**
     * Creates a window containing a connect 4 game
     */
    public void start() {
        // Create empty frame
        JFrame mainWindow = new JFrame("Connect 4");
        mainWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Add content pane to frame
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        mainWindow.setContentPane(contentPane);

        for (int i = 0; i < COLUMNS ; i++) {
            // Create JPanel for each column in the board
            columns[i] = new JPanel();
            columns[i].setLayout(new BoxLayout(columns[i], BoxLayout.Y_AXIS));
            contentPane.add(columns[i]);
            
            // Fill up each column with spaces where pieces can be placed
            for (int j = 0; j < ROWS; j++) {
                /*
                 * Insert each space at index 0 so rows are indexed from
                 * the bottom.
                 */
                columns[i].add(new GameBoardSpace());
            }
        }
        // Make move when columns are clicked
        columnListeners(true);

        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    /**
     * Adds a piece to the column that was clicked.<br>
     * If the column is full no piece is added.
     * 
     * @param e Mouse event passed by a listener
     * on one of the column components.
     * 
     * @see columnListeners
     */
    private void makeMove(MouseEvent e) {
        JPanel column = (JPanel) e.getSource();
        
        // Find first row in the column which contains an empty space
        JLabel emptySpace = null;
        // Iterating backwards otherwise board becomes upside down
        for (int i = ROWS - 1 ; i >= 0 ; i--) {
            JLabel boardSpace = (JLabel) column.getComponent(i);
            if (boardSpace.getIcon() == GameBoardSpace.BLANK) {
                emptySpace = boardSpace;
                break;
            }
        }
        
        // If the column isn't full
        if (emptySpace != null) {
            // Update game state
            emptySpace.setIcon(getTurnIcon());
            if (ConnectPattern.isFourInRow(getBoardData())) {
                victory();
            }
            changeTurn();
        }
    }

    /**
     * Registers or removes mouse listeners with each column in the game board.
     * 
     * @param enable If true listeners are added else they are removed.
     */
    protected void columnListeners(boolean enable) {
        if (enable) {
            for (int i = 0; i < COLUMNS ; i++) {
                columns[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        makeMove(e);
                    }
                });
            }
        }
        else {
            for (int i = 0; i < COLUMNS ; i++) {
                columns[i].removeMouseListener(columns[i].getMouseListeners()[0]);
            }
        }

    }

    /**
     * Starts victory animation
     */
    private void victory() {
        int[][] points = ConnectPattern.isFourInRowDetail(getBoardData());
        Timer timer = new Timer(300, new VictoryActionListener(this, points, columns, getTurnIcon()));
        timer.start();
        // Stop moves during animation
        columnListeners(false);
    }

    /**
     * @return Red if turn is true else yellow
     * 
     * @see turn
     */
    private ImageIcon getTurnIcon() {
        return turn ? GameBoardSpace.RED : GameBoardSpace.YELLOW;
    }

    private void changeTurn() {
        turn = !turn;
    }

    /**
     * @return The state of the pieces on the board represented as an array
     * of chars where each character represents a piece on the game board.
     * 
     * @see iconToChar
     */
    private char[][] getBoardData() {
        char[][] boardData = new char[COLUMNS][ROWS];
        for (int i = 0 ; i < COLUMNS ; i++) {
            for (int j = 0 ; j < ROWS ; j++) {
                JLabel boardSpace = (JLabel) columns[i].getComponent(j);
                boardData[i][j] =  iconToChar(boardSpace.getIcon());
            }
        }
        return boardData;
    }

    /**
     * Converts ImageIcon objects to a character representation
     * 
     * @param icon Icon to be converted to a character
     * 
     * @return Character
     * 
     * @see ConnectPattern
     * @see GameBoardSpace
     */
    protected static char iconToChar(Icon icon) {
        if (icon == GameBoardSpace.BLANK) {
            return ConnectPattern.BLANK;
        }
        else if (icon == GameBoardSpace.RED) {
            return ConnectPattern.RED;
        }
        else if (icon == GameBoardSpace.YELLOW){
            return ConnectPattern.YELLOW;
        }
        else {
            throw new InvalidParameterException("Invalid icon provided.");
        }
    }
}

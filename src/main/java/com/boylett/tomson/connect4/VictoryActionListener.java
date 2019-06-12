package com.boylett.tomson.connect4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controls the victory animation and resets the game board
 * 
 * @auther Tomson Boylett
 */
public class VictoryActionListener implements ActionListener {
    private boolean show = false;
    private int counter = 0;
    private final int[][] points;
    private final JPanel[] columns;
    private final Icon turn;
    private final ConnectGame game;

    public VictoryActionListener(ConnectGame game, int[][] points, JPanel[] columns, Icon turn) {
        this.game = game;
        this.points = points;
        this.columns = columns;
        this.turn = turn;
    }

    /**
     * Causes the pieces to flash several times then resets the game board.
     * 
     * @param e An event from a periodic timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        showHidePoints(points, show);
        show = !show;
        counter++;
        if (counter > 7) {
            ((Timer) e.getSource()).stop();
            clearGame();
            game.columnListeners(true);
        }
    }

    /**
     * Alternates a row of pieces on the game board between the original icon
     * and a blank icon.
     * 
     * @param points Which pieces to show or hide
     * @param show Switch to the original icon or the blank one
     */
    private void showHidePoints(int[][] points, boolean show) {
        for (int[] point : points) {
            JLabel boardSpace = (JLabel) columns[point[0]].getComponent(point[1]);
            if (show) {
                boardSpace.setIcon(turn);
            }
            else {
                boardSpace.setIcon(GameBoardSpace.BLANK);
            }
        }
    }

    /**
     * Sets all the icons on the board back to blank.
     */
    private void clearGame() {
        for (JPanel column : columns) {
            for (int i = 0; i < column.getComponentCount(); i++) {
                ((JLabel) column.getComponent(i)).setIcon(GameBoardSpace.BLANK);
            }
        }
    }
}

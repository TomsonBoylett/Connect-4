package com.boylett.tomson.connect4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tomson on 12/05/2016.
 */
public class VictoryActionListener implements ActionListener {
    private boolean show = false;
    private int counter = 0;
    private int[][] points;
    private JPanel[] columns;
    private Icon turn;

    public VictoryActionListener(int[][] points, JPanel[] columns, Icon turn) {
        this.points = points;
        this.columns = columns;
        this.turn = turn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showHidePoints(points, show);
        show = !show;
        counter++;
        if (counter > 7) {
            ((Timer) e.getSource()).stop();
            clearGame();
            ConnectGame.getInstance().mouseListeners(true);
        }
    }

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

    private void clearGame() {
        for (JPanel column : columns) {
            for (int i = 0; i < column.getComponentCount(); i++) {
                ((JLabel) column.getComponent(i)).setIcon(GameBoardSpace.BLANK);
            }
        }
    }
}

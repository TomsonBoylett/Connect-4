package com.boylett.tomson.connect4;

import javax.swing.*;
import java.awt.event.*;
import java.security.InvalidParameterException;
import java.util.Iterator;

public class ConnectGame {
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    protected static final int NUMBER_IN_ROW = 4;

    private static final ConnectGame INSTANCE = new ConnectGame();

    private boolean turn = true;
    private JPanel[] columns = new JPanel[COLUMNS];

    public static ConnectGame getInstance() {
        return INSTANCE;
    }

    private ConnectGame() {
        // Makes constructor private.
    }

    public void start() {
        JFrame mainWindow = new JFrame("Connect 4");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        mainWindow.setContentPane(contentPane);

        for (int i = 0; i < COLUMNS ; i++) {
            columns[i] = new JPanel();
            columns[i].setLayout(new BoxLayout(columns[i], BoxLayout.Y_AXIS));
            contentPane.add(columns[i]);
            for (int j = 0; j < ROWS; j++) {
                columns[i].add(new GameBoardSpace());
            }
        }
        mouseListeners(true);

        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    private void makeMove(MouseEvent e) {
        JPanel column = (JPanel) e.getSource();
        for (int i = (ROWS - 1) ; i >= 0 ; i--) {
            JLabel boardSpace = (JLabel) column.getComponent(i);
            if (boardSpace.getIcon() == GameBoardSpace.BLANK) {
                boardSpace.setIcon(getTurnIcon());
                TreeNode<GameTreeValue> tree = ConnectPattern.getMoveTree(new TreeNode<GameTreeValue>(null, null), getBoardData(), true, 3);
                Iterator<TreeNode<GameTreeValue>> it = tree.getIterator();
                while (it.hasNext()) {
                    String indent = "";
                    TreeNode<GameTreeValue> node = it.next();
                    TreeNode<GameTreeValue> testNode = node;
                    while (testNode.getParent() != null) {
                        indent += "    ";
                        testNode = testNode.getParent();
                    }
                    System.out.println(indent + node.getValue().getHeuristic() + " " + node.getValue().getPoint()[0] + "," + node.getValue().getPoint()[1]);
                }
                if (ConnectPattern.isFourInRow(getBoardData())) {
                    victory();
                }
                changeTurn();
                break;
            }
        }
    }

    protected void mouseListeners(boolean bool) {
        if (bool) {
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

    private void victory() {
        int[][] points = ConnectPattern.isFourInRowDetail(getBoardData());
        Timer timer = new Timer(300, new VictoryActionListener(points, columns, getTurnIcon()));
        timer.start();
        mouseListeners(false);
    }

    private ImageIcon getTurnIcon() {
        return turn ? GameBoardSpace.RED : GameBoardSpace.YELLOW;
    }

    private void changeTurn() {
        turn = !turn;
    }

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

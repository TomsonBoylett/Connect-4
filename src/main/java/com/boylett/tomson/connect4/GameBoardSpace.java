package com.boylett.tomson.connect4;

import javax.swing.*;
import java.security.InvalidParameterException;

/**
 * Created by Tomson on 12/05/2016.
 */
public class GameBoardSpace extends JLabel{
    public static final ImageIcon BLANK = new ImageIcon(ConnectGame.class.getResource("/blank.png"));
    public static final ImageIcon RED = new ImageIcon(ConnectGame.class.getResource("/red.png"));
    public static final ImageIcon YELLOW = new ImageIcon(ConnectGame.class.getResource("/yellow.png"));

    public GameBoardSpace() {
        super();
        super.setIcon(BLANK);
    }

    public GameBoardSpace(String text) {
        super(text);
    }

    public void setIcon(ImageIcon icon) throws InvalidParameterException{
        if (icon == BLANK ||
                icon == RED ||
                icon == YELLOW) {
            super.setIcon(icon);
        }
        else {
            throw new InvalidParameterException("Invalid board icon provided.");
        }


    }
}

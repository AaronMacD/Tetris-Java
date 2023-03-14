package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Random;

public class GameController {


    public int level;
    public int score;

    private int speed;
    private int[][] playAreaArray = new int[10][22];

    private Piece currentPiece;

    private Piece PieceMaker() {
        Random rand = new Random();
        int num = rand.nextInt(6);
        Piece p;

        //create new piece based on random number
        switch (num) {
            case 0: p = new L_Piece();
                break;
            case 1: p = new Line_Piece();
                break;
            case 2: p = new S_Piece();
                break;
            case 3: p = new Square_Piece();
                break;
            case 4: p = new T_Piece();
                break;
            default: p = new Z_Piece();
                break;
        }

        return p;
    }
}

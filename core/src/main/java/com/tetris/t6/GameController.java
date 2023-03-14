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

}

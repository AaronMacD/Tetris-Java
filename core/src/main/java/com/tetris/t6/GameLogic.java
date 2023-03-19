package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

public class GameLogic {
    public int level;
    public int score;
    private int speed;
    private Piece currentPiece;


    GameLogic(GameScreen gameScreen) {
        //TODO: remove this test
        currentPiece = new Piece();
        gameScreen.drawPiece(currentPiece);
    }


}

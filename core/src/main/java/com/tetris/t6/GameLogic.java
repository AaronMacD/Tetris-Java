package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {
    private Piece currentPiece;
    public int level;
    //four levels of speed to start. cells per frame is 1/speed
    //TODO:add the rest of the speeds, cap out at 10 for now?
    private float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f, 0.035256f};
    private float time_exists;
    private float time_movement;
    public int score;
    private final int singleClear = 100 * level;
    private final int doubleClear = 300 * level;
    private final int tripleClear = 500 * level;
    private final int tetrisClear = 800 * level;
    private int linesCleared;
    private boolean pieceIsActive;
    private BlockShape activePiece;
    //private Piece activePiece;
    HeldBlock heldBlock;
    NextBlock nextBlock;
    SoundController SoundCtrl;


    GameLogic(GameScreen gameScreen) {
        //TODO: remove this test
        currentPiece = new Piece();
        gameScreen.drawPiece(currentPiece);

        level = 1;
        score = 0;
    }

    public void update() {
        if(!pieceIsActive){
            //create a new piece based on 'next block'
            time_exists = 0f;
            time_movement = 0f;
        }
    }

    private void moveDownLogically() {
        time_exists += Gdx.graphics.getDeltaTime();
        while (time_exists > time_movement) {
            if (moveDownPossible()){
                currentPiece.moveDown();

                time_movement += 1 / levelSpeeds[level];
            }
        }
    }

    //TODO: work on combining with rotationPossible to eliminate reused code
    private boolean moveDownPossible(){
        Point[][] dimensions = currentPiece.getDimensions();
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rotationNum = currentPiece.getRotationNum();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;

            //first expression prevents index out of bounds
            if (squareRow+1 < 21 && GameScreen.board[squareRow+1][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    public void moveLeftRight(int lr) {
        if (moveLeftRightPossible(lr)) {
            if (lr == -1) {
                currentPiece.moveLeft();
            }
            else {
                currentPiece.moveRight();
            }
        }
    }

    //TODO: combine with moveDownPossible, include parameters for down, left/right
    //-1 for left, 1 for right
    private boolean moveLeftRightPossible(int lr) {
        Point[][] dimensions = currentPiece.getDimensions();
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rotationNum = currentPiece.getRotationNum();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;

            //first two expressions prevent index out of bounds
            if (squareCol+lr >= 0 && squareCol+lr < 10 &&
                GameScreen.board[squareRow][squareCol+lr].isAvailable()) {
                availableCount++;
            }
        }
        //returns true or false
        return (availableCount == 4);
    }

    public void rotate(int rotationNum, int direction){
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(rotationNum)) {
            currentPiece.setRotationNum(rotationNum);
        }
    }

    private boolean rotationPossible(int rotationNum) {
        Point[][] dimensions = currentPiece.getDimensions();
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;
            if (GameScreen.board[squareRow][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    private void levelUp(){
        if (linesCleared >= 10){
            level++;
            linesCleared = 0;
        }
    }

//    private void clearLine(int[] fullRows){
//        int fullRowsCounter = fullRows[4];
//        linesCleared += fullRowsCounter;
//        switch (fullRowsCounter){
//            case 0: return;
//            case 1: score += singleClear;
//            case 2: score += doubleClear;
//            case 3: score += tripleClear;
//            case 4: score += tetrisClear;
//        }
//
//        fullRows[4] = 99;
//        for(int row : fullRows){
//            if (row > 22){
//                continue;
//            }
//            for(int x = 0; x < 10; x++){
//                logicBoard[x][row] = BlockShape.EMPTY;
//            }

            //Was moving the board down after clearing, but might be easier to just have everything always move down till it can't
//            for(int y = row; y < 21; y++){
//                for (int x = 0; x < 10; x++){
//                    if (logicBoard[x][y+1] > 0){
//                        logicBoard[x][y] = logicBoard[x][y+1];
//                        logicBoard[x][y+1] = 0;
//                    }
//                }
//            }
//        }
//    }


}

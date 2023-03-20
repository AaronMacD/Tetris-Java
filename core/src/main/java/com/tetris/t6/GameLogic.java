package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

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

    /*todo really not happy with how i have this written out, but don't have time to make it better right now
    uses the active block location to check if it's possible to move down
     */

    private void moveDownLogically() {
        time_exists += Gdx.graphics.getDeltaTime();
        while (time_exists > time_movement) {
            if (moveDownPossible()){
                currentPiece.moveDown();
//                logicBoard[4][21] = BlockShape.EMPTY;
//                logicBoard[5][21] = BlockShape.EMPTY;
//                logicBoard[4][22] = BlockShape.EMPTY;
//                logicBoard[5][22] = BlockShape.EMPTY;
//
//                block1Loc[1] += 1;
//                block2Loc[1] += 1;
//                block3Loc[1] += 1;
//                block4Loc[1] += 1;
//
//                logicBoard[block1Loc[0]][block1Loc[1]] = activePiece;
//                logicBoard[block2Loc[0]][block2Loc[1]] = activePiece;
//                logicBoard[block3Loc[0]][block3Loc[1]] = activePiece;
//                logicBoard[block4Loc[0]][block4Loc[1]] = activePiece;

                time_movement += 1 / levelSpeeds[level];
            }
        }
    }

    private boolean moveDownPossible(){
        //TODO
        return true;
    }
    public void rotate(int rNum, int direction){
        IntArray rowcolArr = new IntArray();
        int[][][] dimensions = currentPiece.getDimensions();
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();

        rNum = Math.floorMod(rNum + direction, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (dimensions[rNum][i][j] == 1) {
                    if (GameScreen.board[row+i][col+j].getColor() == Color.BLACK) {
                        rowcolArr.add(row+i,col+j);
                    }
                }
            }
        }

        //if rotation is succesful, draw piece and set rotationNum
        if (rowcolArr.size == 4) {
            drawPiece(coords, this.blockShape, this.orientation);
            currentPiece.setRotationNumber = rNum;
        }
    }

    private void levelUp(){
        if (linesCleared >= 10){
            level++;
            linesCleared = 0;
        }
    }

    private int[] checkRows(){
        int[] fullRows = {99, 99, 99, 99, 0};
        int fullRowsCounter = 0;
        for(int y = 0; y < 20; y++){
            for(int x = 0; x < 10; x++){
                if (logicBoard[x][y] == BlockShape.EMPTY) {
                    break;
                }
                if (x == 10){
                    fullRows[fullRowsCounter] = y;
                    fullRowsCounter++;
                }
            }
        }
        fullRows[4] = fullRowsCounter;
        return fullRows;
    }
    private void clearLine(int[] fullRows){
        int fullRowsCounter = fullRows[4];
        linesCleared += fullRowsCounter;
        switch (fullRowsCounter){
            case 0: return;
            case 1: score += singleClear;
            case 2: score += doubleClear;
            case 3: score += tripleClear;
            case 4: score += tetrisClear;
        }

        fullRows[4] = 99;
        for(int row : fullRows){
            if (row > 22){
                continue;
            }
            for(int x = 0; x < 10; x++){
                logicBoard[x][row] = BlockShape.EMPTY;
            }

            //Was moving the board down after clearing, but might be easier to just have everything always move down till it can't
//            for(int y = row; y < 21; y++){
//                for (int x = 0; x < 10; x++){
//                    if (logicBoard[x][y+1] > 0){
//                        logicBoard[x][y] = logicBoard[x][y+1];
//                        logicBoard[x][y+1] = 0;
//                    }
//                }
//            }
        }
    }


}

package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class GameScreen implements Screen {
    TetrisGame game;
    private Square[][] board;
    public final int ROWS = 22;
    public final int COLS = 10;
    private Piece currentPiece;
    public int level;
    //four levels of speed to start. cells per frame is 1/speed
    //TODO:add the rest of the speeds, cap out at 10 for now?
    private float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f, 0.035256f};
    private float time_exists;
    private float time_movement;
    private int score;
    private final int singleClear = 100 * level;
    private final int doubleClear = 300 * level;
    private final int tripleClear = 500 * level;
    private final int tetrisClear = 800 * level;
    private int linesCleared;
    private boolean pieceIsActive;
    private BlockShape activePiece;
    HeldBlock heldBlock;
    NextBlock nextBlock;
    SoundController SoundCtrl;

    //Sounds

    public GameScreen(TetrisGame game) {
        this.game = game;

        currentPiece = new Piece();

        level = 1;
        score = 0;

        board = new Square[ROWS][COLS];
        //initialize board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j, Color.BLACK);
            }
        }

        drawSquares(currentPiece.getColor());
        //Loading Sounds

        //Loading Music
    }

    @Override
    public void render(float delta) {

//        if (timer)
//        moveDownLogically();

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            moveDownLogically();
            drawSquares(currentPiece.getColor());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            moveLeftRight(-1);
            drawSquares(currentPiece.getColor());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            moveLeftRight(1);
            drawSquares(currentPiece.getColor());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            rotate(1);
            drawSquares(currentPiece.getColor());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rotate(-1);
            drawSquares(currentPiece.getColor());
        }


        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < ROWS - 2; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].drawSquare(game.shapeRenderer);
            }
        }
        game.shapeRenderer.end();
    }

    public void drawSquares(Color color) {

        //row and column for the top-left corner
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rNum = currentPiece.getRotationNum();
        Point[][] dimensions = currentPiece.getDimensions();

        for (int i = 0; i < 4; i++) {
            int squareRow = row + dimensions[rNum][i].x;
            int squareCol = col + dimensions[rNum][i].y;

            board[squareRow][squareCol] = new Square(squareRow, squareCol, color);
        }
    }

//    public void update() {
//        if(!pieceIsActive){
//            //create a new piece based on 'next block'
//            time_exists = 0f;
//            time_movement = 0f;
//        }
//    }

    public void moveDownLogically() {
        time_exists += Gdx.graphics.getDeltaTime();
        while (time_exists > time_movement) {
            if (moveDownPossible()){
                //set current squares to black
                drawSquares(Color.BLACK);
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
            if (squareRow+1 < 21 && board[squareRow+1][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    public void moveLeftRight(int lr) {
        if (moveLeftRightPossible(lr)) {
            if (lr == -1) {
                //set current squares to black
                drawSquares(Color.BLACK);
                //new squares will be set to appropriate color
                currentPiece.moveLeft();
            }
            else {
                drawSquares(Color.BLACK);
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
                board[squareRow][squareCol+lr].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    public void rotate(int direction) {
        int rotationNum = currentPiece.getRotationNum();
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(rotationNum)) {
            drawSquares(Color.BLACK);
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
            //TODO index checking beforehand
            if (board[squareRow][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    private void resetSquares() {

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

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

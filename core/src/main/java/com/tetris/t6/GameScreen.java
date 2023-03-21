package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.IntArray;

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
    private float time_movement = 0f;
    private int score;
    String scoreText;
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

    //tools and testing
    String testText1, testText2, testText3;
    boolean timers_enabled = true;
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

        drawPiece(currentPiece.getColor());
        //Loading Sounds

        //Loading Music
    }

    @Override
    public void render(float delta) {

        moveDownLogically();

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            time_movement = 100f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            moveLeftRight(-1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            moveLeftRight(1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            rotate(1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rotate(-1);
        }

        drawPiece(currentPiece.getColor());
        levelUp();

        scoreText = String.format("Score: %d", this.score);

        game.batch.begin();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].drawSquare(game.drawer);
            }
        }
        game.font.draw(game.batch, scoreText, 300,780);
        game.batch.end();



        //Tools and testing

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            timers_enabled = !timers_enabled;
        }


    }

    public void drawPiece(Color color) {

        //row and column for the top-left corner
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rNum = currentPiece.getRotationNum();
        Point[][] dimensions = currentPiece.getDimensions();

        for (int i = 0; i < 4; i++) {
            int squareRow = row + dimensions[rNum][i].x;
            int squareCol = col + dimensions[rNum][i].y;
            if (squareCol>=0) {
                board[squareRow][squareCol] = new Square(squareRow, squareCol, color);
            }
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
        if (timers_enabled) {
            time_movement += levelSpeeds[level];
        }
        //should be >= 1, normally, but set to 5 for testing purposes.
        while (time_movement >= 5) {
            if (moveDownPossible()) {
                drawPiece(Color.BLACK);
                currentPiece.moveDown();
                time_movement = 0f;
            }
            else {
                lockSquares();
                currentPiece = new Piece();
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
                drawPiece(Color.BLACK);
                //new squares will be set to appropriate color
                currentPiece.moveLeft();
            }
            else {
                drawPiece(Color.BLACK);
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
            drawPiece(Color.BLACK);
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

            if (squareRow < 21 && squareCol >= 0 && squareCol < 10
                && board[squareRow][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    private void lockSquares() {
        IntArray rowsToCheck = new IntArray(4);

        Point[][] dimensions = currentPiece.getDimensions();
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rotationNum = currentPiece.getRotationNum();
        int squareRow;
        int squareCol;

        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;
            board[squareRow][squareCol].setAvailability(false);
            if (!rowsToCheck.contains(squareRow)) {
                rowsToCheck.add(squareRow);
            }
        }

        checkFullRow(rowsToCheck);
    }

    //TODO: currently inefficient because we might check the same row multiple times
    private void checkFullRow(IntArray rowList) {

        IntArray fullRows = new IntArray();
        for (int i = 0; i < rowList.size; i++) {
            int squareCount = 0;
            for (int j = 0; j < 10; j++) {
                if (!board[rowList.get(i)][j].isAvailable()) {
                    squareCount++;
                }
            }

            if (squareCount == 10) {
                fullRows.add(rowList.get(i));
            }
        }
        if(fullRows.notEmpty()){
            clearLine(rowList);
            dropAfterClear(rowList);
        }


    }

    private void levelUp(){
        if (linesCleared >= 10){
            level++;
            linesCleared = 0;
        }
    }

    //TODO: Find a way to calculate the speed based on level etc instead of using an array to hold values.
    //Not sure what the equation is to scale the speed, or if there even is a way to copy the same from the actual game.

    public float changeSpeed(int lvl){ // Might be useful to call to change the speed with the level.
        float speed = levelSpeeds[lvl]; //Looks at the position of the array and sets speed to that level value speed.
        return speed; //return the level's speed.
    }

    private void clearLine(IntArray rowList) {
        switch (rowList.size) {
            case 0:
                return;
            case 1:
                score += singleClear;
            case 2:
                score += doubleClear;
            case 3:
                score += tripleClear;
            case 4:
                score += tetrisClear;
        }

        for (int row : rowList.items) {
            for (int x = 0; x < 10; x++) {
                board[row][x].setColor(Color.BLACK);
                board[row][x].setAvailability(true);
            }
        }
    }

    private void dropAfterClear(IntArray rowList){
        rowList.sort();
        for (int row : rowList.items){
            for (int i = row; i > 0; i--) {
                for (int j = 0; j < COLS; j++) {
                    board[i][j].setColor(board[i-1][j].getColor());
                    board[i][j].setAvailability(board[i-1][j].isAvailable());
                }
            }
        }
    }



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

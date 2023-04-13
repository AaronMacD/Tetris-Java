package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.logging.Level;

public class GameScreen implements Screen {
    TetrisGame game;
    private Square[][] board;
    public final int ROWS = 22;
    public final int COLS = 10;
    private Piece currentPiece;
    private int level = 1;
    private String levelText;
    //four levels of speed to start. cells per frame is 1/speed
    //TODO:add the rest of the speeds, cap out at 10 for now?
    private final float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f, 0.035256f};
    private float time_controls;
    private float time_movement;
    private int score;
    private String scoreText;
    private int linesCleared;
    private boolean pieceIsActive;

    HeldBlock heldBlock;
    NextBlock nextBlock;
    Sound lock;
    Sound rotate;
    Sound line_clear;
    Sound tetris;
    Music victory1;

    //Sounds

    //tools and testing
    String testText1, testText2, testText3;
    boolean timers_enabled = true;
    public GameScreen(TetrisGame game) {
        this.game = game;

        currentPiece = new Piece();
        nextBlock = new NextBlock();
        heldBlock = new HeldBlock();

        level = 1;
        score = 0;

        board = new Square[ROWS][COLS];
        //initialize board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j, Color.BLACK);
            }
        }

        //Loading Sounds
        lock = Gdx.audio.newSound(Gdx.files.internal("lock.wav"));
        rotate = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        line_clear = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetris = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));

        //Loading Music
        victory1 = Gdx.audio.newMusic(Gdx.files.internal("Victory1.wav"));
        victory1.play();
        victory1.setVolume(0.50f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0, 0.2f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        time_controls += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            hardDrop();
            time_movement = 100f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            time_movement = 100f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && time_controls > 0.15f) {
            moveLeftRight(-1);
            time_controls = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && time_controls > 0.15f) {
            moveLeftRight(1);
            time_controls = 0f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            rotate(1);
            rotate.play(1.0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rotate(-1);
            rotate.play(1.0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            drawPiece(Color.BLACK);
            if(heldBlock.getHeldPiece() == null){
                heldBlock.setHeldPiece(currentPiece);
                currentPiece = nextBlock.getNextPiece();
                nextBlock.generateNextPiece();
            }
            else{
                currentPiece = heldBlock.swapPiece(currentPiece);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            this.hide();
            game.setScreen(new PauseScreen(game, this));
        }

        drawPiece(currentPiece.getColor());
        levelUp();

        moveDownLogically();

        scoreText = String.format("Score: %d", this.score);
        levelText = String.format("Level: %d", this.level);

        game.batch.begin();
        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].drawSquare(game.drawer);
            }
        }


        heldBlock.drawNext(game.drawer);
        nextBlock.drawNext(game.drawer);

        game.font.draw(game.batch, scoreText, 300,780);
        game.font.draw(game.batch, levelText, 301,795);
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
            //if (squareCol>=0) {
                //board[squareRow][squareCol] = new Square(squareRow, squareCol, color);
                board[squareRow][squareCol].setColor(color);
            //}
        }
    }


    public void moveDownLogically() {
        if (timers_enabled) {
            time_movement += levelSpeeds[level];
        }
        //should be >= 1, normally, but set to 5 for testing purposes.
        while (time_movement >= 1) {
            if (moveDownPossible()) {
                drawPiece(Color.BLACK);
                currentPiece.moveDown();
                time_movement = 0f;
            }
            else {
                lockSquares();
                currentPiece = nextBlock.getNextPiece();
                nextBlock.generateNextPiece();
                time_movement = 0f;
            }
        }
    }

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

    private void hardDrop() {
        while(moveDownPossible()) {
            moveDownLogically();
        }
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
            clearLine(fullRows);
            dropAfterClear(fullRows);
        }
        else {
            lock.play(1.0f);
            checkLoss();
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
                score += 100 * level;
                line_clear.play(1.0f);
                break;
            case 2:
                score += 300 * level;
                line_clear.play(1.0f);
                break;
            case 3:
                score += 500 * level;
                line_clear.play(1.0f);
                break;
            case 4:
                score += 800 * level;
                tetris.play(1.0f);
                break;
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
        checkLoss();
    }

    //TODO: change so that it doesn't just exit
    private void checkLoss() {
//        if (currentPiece.getRow() == 0) {
//            System.exit(0);
//        }

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 10; j++){
                if (!board[i][j].isAvailable()){
                    this.pause();
                    this.hide();
                    game.setScreen(new LossScreen(game));
                    this.dispose();
                    return;
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

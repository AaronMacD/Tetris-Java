package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.IntArray;
import java.awt.Point;


public final class GameScreen implements Screen {
    /**
     * Instance of the game.
     */
    private TetrisGame game;
    /**
     * 2D array of squares to represent the game board.
     */
    private Square[][] board;
    /**
     * Number of rows in the board.
     */
    private final int ROWS = 22;
    /**
     * Number of columns in the board.
     */
    private final int COLS = 10;
    /**
     * The piece that is currently falling.
     */
    private Piece currentPiece;
    /**
     * Level counter.
     */
    private int level;

    /**
     * The text to display the current level.
     */
    private String levelText;
    /**
     * Speeds for pieces to fall for different levels.
     * Cells per frame is 1/speed.
     */
    private final float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f,
        0.035256f};
    private float timeControls;
    private float timeMovement;
    private int score;
    private String scoreText;
    private int linesCleared;
    private final int linesToLevelUp = 10;
    private HeldBlock heldBlock;
    private NextBlock nextBlock;
    //Sounds
    private Sound lock;
    private Sound rotate;
    private Sound lineClear;
    private Sound tetris;
    private Music victory1;
    private boolean timersEnabled = true;
    public GameScreen(final TetrisGame aGame) {
        this.game = aGame;

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
        lineClear = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetris = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));

        //Loading Music
        victory1 = Gdx.audio.newMusic(Gdx.files.internal("Victory1.wav"));
        victory1.setLooping(true);
        victory1.play();
        victory1.setVolume(0.50f);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        timeControls += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            hardDrop();
            timeMovement = 100f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            timeMovement = 100f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && timeControls > 0.15f) {
            moveLeftRight(-1);
            timeControls = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && timeControls > 0.15f) {
            moveLeftRight(1);
            timeControls = 0f;
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
            if (heldBlock.getHeldPiece() == null) {
                heldBlock.setHeldPiece(currentPiece);
                currentPiece = nextBlock.getNextPiece();
                nextBlock.generateNextPiece();
            } else {
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

        game.font.draw(game.batch, scoreText, 300, 780);
        game.font.draw(game.batch, levelText, 301, 795);
        game.batch.end();



        //Tools and testing

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            timersEnabled = !timersEnabled;
        }


    }

    public void drawPiece(final Color color) {
        //row and column for the top-left corner
        int row = currentPiece.getRow();
        int col = currentPiece.getCol();
        int rNum = currentPiece.getRotationNum();
        Point[][] dimensions = currentPiece.getDimensions();

        for (int i = 0; i < 4; i++) {
            int squareRow = row + dimensions[rNum][i].x;
            int squareCol = col + dimensions[rNum][i].y;
                board[squareRow][squareCol].setColor(color);
        }
    }


    public void moveDownLogically() {
        if (timersEnabled) {
            timeMovement += levelSpeeds[level];
        }
        //should be >= 1, normally, but set to 5 for testing purposes.
        while (timeMovement >= 1) {
            if (moveDownPossible()) {
                drawPiece(Color.BLACK);
                currentPiece.moveDown();
            } else {
                lockSquares();
                currentPiece = nextBlock.getNextPiece();
                nextBlock.generateNextPiece();
            }
            timeMovement = 0f;
        }
    }

    //TODO prevent pieces from overlapping at spawn
    private boolean moveDownPossible() {
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
            if (squareRow + 1 < ROWS - 1
                && board[squareRow + 1][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    private void hardDrop() {
        while (moveDownPossible()) {
            moveDownLogically();
        }
    }

    public void moveLeftRight(final int leftRight) {
        if (moveLeftRightPossible(leftRight)) {
            if (leftRight == -1) {
                //set current squares to black
                drawPiece(Color.BLACK);
                //new squares will be set to appropriate color
                currentPiece.moveLeft();
            } else {
                drawPiece(Color.BLACK);
                currentPiece.moveRight();
            }
        }
    }

    //-1 for left, 1 for right
    private boolean moveLeftRightPossible(final int leftRight) {
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
            if (squareCol + leftRight >= 0 && squareCol + leftRight < COLS
                && board[squareRow][squareCol + leftRight].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return (availableCount == 4);
    }

    public void rotate(final int direction) {
        int rotationNum = currentPiece.getRotationNum();
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(rotationNum)) {
            drawPiece(Color.BLACK);
            currentPiece.setRotationNum(rotationNum);
        }
    }

    private boolean rotationPossible(final int rotationNum) {
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

            if (squareRow < ROWS - 1 && squareCol >= 0 && squareCol < COLS
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

    private void checkFullRow(final IntArray rowList) {

        IntArray fullRows = new IntArray();
        for (int i = 0; i < rowList.size; i++) {
            int squareCount = 0;
            for (int j = 0; j < COLS; j++) {
                if (!board[rowList.get(i)][j].isAvailable()) {
                    squareCount++;
                }
            }

            if (squareCount == COLS) {
                fullRows.add(rowList.get(i));
            }
        }
        if (fullRows.notEmpty()) {
            clearLine(fullRows);
            dropAfterClear(fullRows);
        } else {
            lock.play(1.0f);
            checkLoss();
        }
    }

    private void levelUp() {
        if (linesCleared >= linesToLevelUp) {
            level++;
            linesCleared = 0;
        }
    }

    public float changeSpeed(final int lvl) { // Might be useful to call to change the speed with the level
        //Looks at the position of the array and sets speed to that level value speed
        float speed = levelSpeeds[lvl];
        return speed; //return the level's speed
    }

    private void clearLine(final IntArray rowList) {
        switch (rowList.size) {
            case 1:
                score += 100 * level;
                lineClear.play(1.0f);
                break;
            case 2:
                score += 300 * level;
                lineClear.play(1.0f);
                break;
            case 3:
                score += 500 * level;
                lineClear.play(1.0f);
                break;
            case 4:
                score += 800 * level;
                tetris.play(1.0f);
                break;
            default:
                return;
        }

        for (int row : rowList.items) {
            for (int x = 0; x < COLS; x++) {
                board[row][x].setColor(Color.BLACK);
                board[row][x].setAvailability(true);
            }
        }
    }

    private void dropAfterClear(final IntArray rowList) {
        rowList.sort();
        for (int row : rowList.items) {
            for (int i = row; i > 0; i--) {
                for (int j = 0; j < COLS; j++) {
                    board[i][j].setColor(board[i - 1][j].getColor());
                    board[i][j].setAvailability(board[i - 1][j].isAvailable());
                }
            }
        }
        checkLoss();
    }

    private void checkLoss() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].isAvailable()) {
                    victory1.stop();
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
    public void resize(final int width, final int height) {

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

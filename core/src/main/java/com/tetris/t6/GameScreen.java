package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.IntArray;
import java.awt.Point;


/**
 * The type Game screen.
 */
public final class GameScreen implements Screen { //NOPMD - suppressed GodClass - TODO explain reason for suppression
    /**
     * Instance of the game.
     */
    private final TetrisGame game;
    /**
     * 2D array of squares to represent the game board.
     */
    private final Square[][] board;
    /**
     * Number of rows in the board.
     */
    private static final int ROWS = 22;
    /**
     * Number of columns in the board.
     */
    private static final int COLS = 10;
    /**
     * The piece that is currently falling.
     */
    private Piece currentPiece;
    /**
     * Level counter.
     */
    private int level;

    /**
     * Speeds for pieces to fall for different levels.
     * Cells per frame is 1/speed.
     */
    private final float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f,
        0.035256f, 0.04693f, 0.06361f, 0.0879f, 0.1236f, 0.1775f, 0.2598f};
    /**
     * Timer for left/right inputs.
     */
    private float timeControls;
    /**
     * Timer for falling speeds.
     */
    private float timeMovement;
    /**
     * Player's current score.
     */
    private int score;
    /**
     * Number of lines cleared.
     */
    private int linesCleared;
    /**
     * Number of lines cleared needed to level up.
     */
    private static final int LINESTOLEVELUP = 10;
    /**
     * The highest level in the game.
     */
    private static final int MAX_LEVEL = 10;
    /**
     * Block that the player holds for later use.
     */
    private final HeldBlock heldBlock;
    /**
     * Boolean for whether swap was used to get the current piece.
     */
    private boolean swapUsed;
    /**
     * Block in the "next" slot that will spawn after current block locks.
     */
    private final NextBlock nextBlock;
    //Sounds
    /**
     * Lock sound effect.
     */
    private final Sound lockSFX;
    /**
     * Rotate sound effect.
     */
    private final Sound rotateSFX;
    /**
     * Line clear sound effect.
     */
    private final Sound lineClearSFX;
    /**
     * Tetris (four lines cleared) sound effect.
     */
    private final Sound tetrisSFX;
    /**
     * Hold piece sound effect.
     */
    private final Sound holdSFX;
    /**
     * Game music.
     */
    private final Music victory1Music;

    private boolean timersEnabled = true;

    /**
     * Texture for the background.
     */
    private final Texture background;

    /**
     * The loss screen.
     */
    private LossScreen lossScreen;

    /**
     * Instantiates a new Game screen.
     *
     * @param aGame the game
     */
    public GameScreen(final TetrisGame aGame) {
        this.game = aGame;

        currentPiece = new Piece();
        nextBlock = new NextBlock();
        heldBlock = new HeldBlock();
        swapUsed = false;

        level = 1;
        score = 0;

        board = new Square[ROWS][COLS];
        //initialize board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j, Color.BLACK);
            }
        }
        //Load screens
        lossScreen = new LossScreen(game);

        //Load graphical assets
        background = new Texture(Gdx.files.internal("bg_gamescreen.png"));

        //Loading Sounds
        lockSFX = Gdx.audio.newSound(Gdx.files.internal("lock.wav"));
        rotateSFX = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        lineClearSFX = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetrisSFX = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));
        holdSFX = Gdx.audio.newSound(Gdx.files.internal("hold.wav"));

        //Loading Music
        victory1Music = Gdx.audio.newMusic(Gdx.files.internal("Victory1.wav"));
        victory1Music.setLooping(true);
        victory1Music.play();
        victory1Music.setVolume(0.30f);
    }

    /**
     * Renders the game screen every "delta" seconds. Handles player input.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(final float delta) { //NOPMD - suppressed CognitiveComplexity - TODO explain reason for suppression //NOPMD - suppressed CyclomaticComplexity - TODO explain reason for suppression
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
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rotate(-1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            drawPiece(Color.BLACK);
            if (heldBlock.getHeldPiece() == null) {
                heldBlock.setHeldPiece(currentPiece);
                currentPiece = nextBlock.getNextPiece();
                nextBlock.generateNextPiece();
                holdSFX.play();
            } else {
                if (!swapUsed) {
                    currentPiece = heldBlock.swapPiece(currentPiece);
                    holdSFX.play();
                }
            }

            swapUsed = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            this.hide();
            game.setScreen(new PauseScreen(game, this));
        }

        drawPiece(currentPiece.getColor());
        if (level < MAX_LEVEL) {
            levelUp();
        }


        moveDownLogically();

        String scoreText = String.format("Score: %d", this.score);
        String levelText = String.format("Level: %d", this.level);
        String linesClearedText = String.format("Lines Cleared: %d",
            this.linesCleared);
        String heldText = "Held Block";
        String nextText = "Next Block";
        String controlsText = "Controls : \n"
            + "Move Left: Left Arrow\n"
            + "Move Right: Right Arrow\n"
            + "Soft Drop: Down Arrow\n"
            + "Hard Drop: Up Arrow\n"
            + "Rotate Clockwise: F Key\n"
            + "Rotate C-Clockwise: D Key\n"
            + "Hold Block: S Key\n"
            + "Pause Menu: Escape Key";

        game.batch.begin();
        //draw bg first
        game.batch.draw(background, 0, 0);

        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].drawSquare(game.drawer);
            }
        }


        heldBlock.drawNext(game.drawer);
        nextBlock.drawNext(game.drawer);

        game.font.draw(game.batch, scoreText, 30, 825);
        game.font.draw(game.batch, levelText,  175, 825);
        game.font.draw(game.batch, linesClearedText, 300, 825);
        game.font.draw(game.batch, heldText, 495, 460);
        game.font.draw(game.batch, nextText, 495, 700);
        game.font.draw(game.batch, controlsText, 450, 300);
        game.batch.end();



        //Tools and testing

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            timersEnabled = !timersEnabled;
        }


    }

    /**
     * Draw the four Squares of a piece based on current state.
     *
     * @param color the color
     */
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


    /**
     * Checks if it's possible to move currentPiece down.
     * If so, calls drawPiece and updates coordinates. If not, calls
     * lockSquares and generates a new piece.
     */
    public void moveDownLogically() {
        if (timersEnabled) {
            timeMovement += levelSpeeds[level];
        }
        //>=1 is default, but can be adjusted to reduce difficulty. 1 seems pretty fast.
        while (timeMovement >= 2.5) {
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

    /**
     * Checks if the four squares directly below the currentPiece's position are
     * available and returns a boolean.
     * @return true if all four squares below the piece are available
     */
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
        return availableCount == 4;
    }

    /**
     * Drops piece as far down the board as possible.
     */
    private void hardDrop() {
        while (moveDownPossible()) {
            moveDownLogically();
        }
    }

    /**
     * Move left or right depending on parameter.
     *
     * @param leftRight -1 to move left, 1 for right.
     */
    private void moveLeftRight(final int leftRight) {
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

    /**
     * Checks if possible to move all four squares of currentPiece left/right.
     * @param leftRight -1 for left, 1 for right
     * @return true or false depending on availability of all four squares.
     */
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
        return availableCount == 4;
    }

    /**
     * Rotates the current piece.
     *
     * @param direction -1 for counterclockwise, 1 for clockwise.
     */
    public void rotate(final int direction) {
        int rotationNum = currentPiece.getRotationNum();
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(rotationNum)) {
            drawPiece(Color.BLACK);
            currentPiece.setRotationNum(rotationNum);
            rotateSFX.play(1.0f);
        }
    }

    /**
     * Checks if all four squares are available to rotate into.
     * @param rotationNum To access appropriate rotation state from 2D array.
     * @return true if all four squares are available to rotate into.
     */
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
        return availableCount == 4;
    }

    /**
     * Makes all four squares of currentPiece unavailable.
     * Calls checkFullRow in case a line needs to be cleared.
     */
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

        //reset to allow swap for new piece
        swapUsed = false;

        checkFullRow(rowsToCheck);
    }

    /**
     * Checks each row in a list to see if they are full.
     * If so, calls clearLine() for those rows. Otherwise calls checkLoss().
     * @param rowList List of rows to check.
     */
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
            lockSFX.play(1.0f);
            checkLoss();
        }
    }

    /**
     * Increases the level if a certain number of lines have been cleared.
     */
    private void levelUp() {
        if (linesCleared >= LINESTOLEVELUP) {
            level++;
            linesCleared = 0;
        }
    }

    private void clearLine(final IntArray rowList) {
        switch (rowList.size) {
            case 1:
                score += 100 * level;
                lineClearSFX.play(1.0f);
                linesCleared += 1;
                break;
            case 2:
                score += 300 * level;
                lineClearSFX.play(1.0f);
                linesCleared += 2;
                break;
            case 3:
                score += 500 * level;
                lineClearSFX.play(1.0f);
                linesCleared += 3;
                break;
            case 4:
                score += 800 * level;
                tetrisSFX.play(1.0f);
                linesCleared += 4;
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
                    victory1Music.stop();
                    this.pause();
                    this.hide();
                    game.setScreen(lossScreen);
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

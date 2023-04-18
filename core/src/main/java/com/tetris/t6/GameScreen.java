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
    //todo comments
    private int numPlayers;
    /**
     * 2D array of squares to represent the game board.
     */
    //private final Square[][] board_p1;

    /**
     * 2D array of squares to represent the game board.
     */
    //private final Square[][] board_p2;

    //private final int p2HorizontalOffset = 16;
    /**
     * Number of p1.ROWS in the board.
     */
    //private static final int p1.ROWS = 22;
    /**
     * Number of columns in the board.
     */
    //private static final int COLS = 10;
    /**
     * The piece that is currently falling.
     */
    //private Piece p1.currentPiece;
    /**
     * The piece that is currently falling.
     */
    //private Piece currentPiece_p2;
    /**
     * Level counter.
     */
    //private int p1.level;
    /**
     * Level counter.
     */
    //private int level_p2;

    /**
     * Speeds for pieces to fall for different levels.
     * Cells per frame is 1/speed.
     */
    private final float[] levelSpeeds = {0.01667f, 0.021017f, 0.026977f,
        0.035256f, 0.04693f, 0.06361f, 0.0879f, 0.1236f, 0.1775f, 0.2598f};
    /**
     * Timer for left/right inputs.
     */
    //private float p1.timeControls;
    /**
     * Timer for falling speeds.
     */
    //private float p1.timeMovement;
    /**
     * Player's current score.
     */
    //private int p1.score;
    /**
     * Number of lines cleared.
     */
    //private int p1.linesCleared;
    /**
     * Timer for left/right inputs.
     */
    //private float timeControls_p2;
    /**
     * Timer for falling speeds.
     */
    //private float timeMovement_p2;
    /**
     * Player's current score.
     */
    //private int score_p2;
    /**
     * Number of lines cleared.
     */
    //private int linesCleared_p2;
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
    //private final HeldBlock p1.heldBlock;
    /**
     * Boolean for whether swap was used to get the current piece.
     */
    //private boolean p1.swapUsed;
    /**
     * Block in the "next" slot that will spawn after current block locks.
     */
    //private final NextBlock p1.nextBlock;
    /**
     * Block that the player holds for later use.
     */
    //private final HeldBlock heldBlock_p2;
    /**
     * Boolean for whether swap was used to get the current piece.
     */
    //private boolean swapUsed_p2;
    /**
     * Block in the "next" slot that will spawn after current block locks.
     */
    //private final NextBlock nextBlock_p2;

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
    private final Texture background2;

    /**
     * The loss screen.
     */
    private LossScreen lossScreen;

    /**
     * Instantiates a new Game screen.
     *
     * @param aGame the game
     */

    private PlayerData p1;
    private PlayerData p2;

    public GameScreen(final TetrisGame aGame, int playerNum) {
        this.numPlayers = playerNum;
        this.game = aGame;
        p1 = new PlayerData(1);
        p2 = new PlayerData(2);

        //Load graphical assets
        background = new Texture(Gdx.files.internal("bg_gamescreen.png"));
        background2 = new Texture(Gdx.files.internal("bg2_gamescreen.png"));

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




        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 1 Functions//////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        p1.timeControls += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            hardDrop(p1);
            p1.timeMovement = 100f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            p1.timeMovement = 100f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && p1.timeControls > 0.15f) {
            moveLeftRight(p1,-1);
            p1.timeControls = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p1.timeControls > 0.15f) {
            moveLeftRight(p1,1);
            p1.timeControls = 0f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            rotate(p1,1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rotate(p1,-1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            drawPiece(p1,Color.BLACK);
            if (p1.heldBlock.getHeldPiece() == null) {
                p1.heldBlock.setHeldPiece(p1.currentPiece);
                p1.currentPiece = p1.nextBlock.getNextPiece();
                p1.nextBlock.generateNextPiece();
                holdSFX.play();
            } else {
                if (!p1.swapUsed) {
                    p1.currentPiece = p1.heldBlock.swapPiece(p1.currentPiece);
                    holdSFX.play();
                }
            }

            p1.swapUsed = true;
        }
        drawPiece(p1, p1.currentPiece.getColor());
        if (p1.level < MAX_LEVEL) {
            levelUp(p1);
        }
        moveDownLogically(p1);

        String scoreText = String.format("Score: %d", this.p1.score);
        String levelText = String.format("Level: %d", this.p1.level);
        String linesClearedText = String.format("Lines Cleared: %d",
            this.p1.linesCleared);
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

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 2 Functions//////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (numPlayers == 2){
            p2.timeControls += delta;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
                hardDrop(p2);
                p2.timeMovement = 100f;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
                p2.timeMovement = 100f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4) && p2.timeControls > 0.15f) {
                moveLeftRight(p2,-1);
                p2.timeControls = 0f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6) && p2.timeControls > 0.15f) {
                moveLeftRight(p2,1);
                p2.timeControls = 0f;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)) {
                rotate(p2,1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7)) {
                rotate(p2,-1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
                drawPiece(p2,Color.BLACK);
                if (p2.heldBlock.getHeldPiece() == null) {
                    p2.heldBlock.setHeldPiece(p2.currentPiece);
                    p2.currentPiece = p2.nextBlock.getNextPiece();
                    p2.nextBlock.generateNextPiece();
                    holdSFX.play();
                } else {
                    if (!p2.swapUsed) {
                        p2.currentPiece = p2.heldBlock.swapPiece(p2.currentPiece);
                        holdSFX.play();
                    }
                }

                p2.swapUsed = true;
            }
            drawPiece(p2, p2.currentPiece.getColor());
            if (p2.level < MAX_LEVEL) {
                levelUp(p2);
            }
            moveDownLogically(p2);
        }
        String scoreText2 = String.format("Score: %d", this.p2.score);
        String levelText2 = String.format("Level: %d", this.p2.level);
        String linesClearedText2 = String.format("Lines Cleared: %d",
            this.p2.linesCleared);
        String heldText2 = "Held Block";
        String nextText2 = "Next Block";
        String controlsText2 = "Controls : \n"
            + "Move Left: Numpad 4\n"
            + "Move Right: Numpad 6\n"
            + "Soft Drop: Numpad 2\n"
            + "Hard Drop: Numpad 8\n"
            + "Rotate Clockwise: Numpad 9\n"
            + "Rotate C-Clockwise: Numpad 7\n"
            + "Hold Block: Numpad 0\n"
            + "Pause Menu: Escape Key";



        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            this.hide();
            game.setScreen(new PauseScreen(game, this));
        }





        /////////////////////////////Drawing//////////////////////////////
        game.batch.begin();
        //draw bg first
        game.batch.draw(background, 0, 0);
        if (numPlayers == 2){
            game.batch.draw(background2, 650,0);
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 1 Drawing////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 1; i < p1.ROWS; i++) {
            for (int j = 0; j < p1.COLS; j++) {
                p1.board[i][j].drawSquare(game.drawer);
            }
        }


        p1.heldBlock.drawNext(game.drawer);
        p1.nextBlock.drawNext(game.drawer);

        game.font.draw(game.batch, scoreText, 30, 825);
        game.font.draw(game.batch, levelText,  175, 825);
        game.font.draw(game.batch, linesClearedText, 300, 825);
        game.font.draw(game.batch, heldText, 495, 540);
        game.font.draw(game.batch, nextText, 495, 780);
        game.font.draw(game.batch, controlsText, 420, 300);



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 2 Drawing////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 1; i < p2.ROWS; i++) {
            for (int j = 0; j < p2.COLS; j++) {
                p2.board[i][j].drawSquare(game.drawer);
            }
        }


        p2.heldBlock.drawNext(game.drawer);
        p2.nextBlock.drawNext(game.drawer);

        game.font.draw(game.batch, scoreText2, 30+650, 825);
        game.font.draw(game.batch, levelText2,  175+650, 825);
        game.font.draw(game.batch, linesClearedText2, 300+650, 825);
        game.font.draw(game.batch, heldText2, 495+650, 540);
        game.font.draw(game.batch, nextText2, 495+650, 780);
        game.font.draw(game.batch, controlsText2, 420+650, 300);



        game.batch.end();

        //Tools and testing

//        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
//            timersEnabled = !timersEnabled;
//        }


    }

    /**
     * Draw the four Squares of a piece based on current state.
     *
     * @param color the color
     */
    public void drawPiece(PlayerData player, Color color) {
        //row and column for the top-left corner
        int row = player.currentPiece.getRow();
        int col = player.currentPiece.getCol();
        int rNum = player.currentPiece.getRotationNum();
        Point[][] dimensions = player.currentPiece.getDimensions();

        for (int i = 0; i < 4; i++) {
            int squareRow = row + dimensions[rNum][i].x;
            int squareCol = col + dimensions[rNum][i].y;
            player.board[squareRow][squareCol].setColor(color);
        }
    }


    /**
     * Checks if it's possible to move currentPiece down.
     * If so, calls drawPiece and updates coordinates. If not, calls
     * lockSquares and generates a new piece.
     */
    public void moveDownLogically(PlayerData player) {
        if (timersEnabled) {
            player.timeMovement += levelSpeeds[player.level];
        }
        //>=1 is default, but can be adjusted to reduce difficulty. 1 seems pretty fast.
        while (player.timeMovement >= 2.5) {
            if (moveDownPossible(player)) {
                drawPiece(player, Color.BLACK);
                player.currentPiece.moveDown();
            } else {
                lockSquares(player);
                player.currentPiece = player.nextBlock.getNextPiece();
                player.nextBlock.generateNextPiece();
            }
            player.timeMovement = 0f;
        }
    }

    //TODO prevent pieces from overlapping at spawn

    /**
     * Checks if the four squares directly below the currentPiece's position are
     * available and returns a boolean.
     * @return true if all four squares below the piece are available
     */
    private boolean moveDownPossible(PlayerData player) {
        Point[][] dimensions = player.currentPiece.getDimensions();
        int row = player.currentPiece.getRow();
        int col = player.currentPiece.getCol();
        int rotationNum = player.currentPiece.getRotationNum();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;

            //first expression prevents index out of bounds
            if (squareRow + 1 < p1.ROWS - 1
                && player.board[squareRow + 1][squareCol].isAvailable()) {
                availableCount++;
            }
        }

        //returns true or false
        return availableCount == 4;
    }

    /**
     * Drops piece as far down the board as possible.
     */
    private void hardDrop(PlayerData player) {
        while (moveDownPossible(player)) {
            moveDownLogically(player);
        }
    }

    /**
     * Move left or right depending on parameter.
     *
     * @param leftRight -1 to move left, 1 for right.
     */
    private void moveLeftRight(PlayerData player, int leftRight) {
        if (moveLeftRightPossible(player, leftRight)) {
            if (leftRight == -1) {
                //set current squares to black
                drawPiece(player, Color.BLACK);
                //new squares will be set to appropriate color
                player.currentPiece.moveLeft();
            } else {
                drawPiece(player, Color.BLACK);
                player.currentPiece.moveRight();
            }
        }
    }

    /**
     * Checks if possible to move all four squares of currentPiece left/right.
     * @param leftRight -1 for left, 1 for right
     * @return true or false depending on availability of all four squares.
     */
    private boolean moveLeftRightPossible(PlayerData player, final int leftRight) {
        Point[][] dimensions = player.currentPiece.getDimensions();
        int row = player.currentPiece.getRow();
        int col = player.currentPiece.getCol();
        int rotationNum = player.currentPiece.getRotationNum();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;

            //first two expressions prevent index out of bounds
            if (squareCol + leftRight >= 0 && squareCol + leftRight < p1.COLS
                && player.board[squareRow][squareCol + leftRight].isAvailable()) {
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
    public void rotate(PlayerData player, final int direction) {
        int rotationNum = player.currentPiece.getRotationNum();
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(player, rotationNum)) {
            drawPiece(player, Color.BLACK);
            player.currentPiece.setRotationNum(rotationNum);
            rotateSFX.play(1.0f);
        }
    }

    /**
     * Checks if all four squares are available to rotate into.
     * @param rotationNum To access appropriate rotation state from 2D array.
     * @return true if all four squares are available to rotate into.
     */
    private boolean rotationPossible(PlayerData player, final int rotationNum) {
        Point[][] dimensions = player.currentPiece.getDimensions();
        int row = player.currentPiece.getRow();
        int col = player.currentPiece.getCol();
        int squareRow;
        int squareCol;
        int availableCount = 0;

        //loop through the four squares we are checking
        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;

            if (squareRow < p1.ROWS - 1 && squareCol >= 0 && squareCol < p1.COLS
                && player.board[squareRow][squareCol].isAvailable()) {
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
    private void lockSquares(PlayerData player) {
        IntArray rowsToCheck = new IntArray(4);

        Point[][] dimensions = player.currentPiece.getDimensions();
        int row = player.currentPiece.getRow();
        int col = player.currentPiece.getCol();
        int rotationNum = player.currentPiece.getRotationNum();
        int squareRow;
        int squareCol;

        for (int i = 0; i < 4; i++) {
            squareRow = row + dimensions[rotationNum][i].x;
            squareCol = col + dimensions[rotationNum][i].y;
            player.board[squareRow][squareCol].setAvailability(false);
            if (!rowsToCheck.contains(squareRow)) {
                rowsToCheck.add(squareRow);
            }
        }

        //reset to allow swap for new piece
        player.swapUsed = false;

        checkFullRow(player, rowsToCheck);
    }

    /**
     * Checks each row in a list to see if they are full.
     * If so, calls clearLine() for those p1.ROWS. Otherwise calls checkLoss().
     * @param rowList List of p1.ROWS to check.
     */
    private void checkFullRow(PlayerData player,final IntArray rowList) {
        IntArray fullRows = new IntArray();
        for (int i = 0; i < rowList.size; i++) {
            int squareCount = 0;
            for (int j = 0; j < p1.COLS; j++) {
                if (!player.board[rowList.get(i)][j].isAvailable()) {
                    squareCount++;
                }
            }

            if (squareCount == p1.COLS) {
                fullRows.add(rowList.get(i));
            }
        }
        if (fullRows.notEmpty()) {
            clearLine(player, fullRows);
            dropAfterClear(player, fullRows);
        } else {
            lockSFX.play(1.0f);
            checkLoss(player);
        }
    }

    /**
     * Increases the level if a certain number of lines have been cleared.
     */
    private void levelUp(PlayerData player) {
        if (player.linesCleared >= LINESTOLEVELUP) {
            player.level++;
            player.linesCleared = 0;
        }
    }

    private void clearLine(PlayerData player, IntArray rowList) {
        switch (rowList.size) {
            case 1:
                player.score += 100 * player.level;
                lineClearSFX.play(1.0f);
                player.linesCleared += 1;
                break;
            case 2:
                player.score += 300 * player.level;
                lineClearSFX.play(1.0f);
                player.linesCleared += 2;
                break;
            case 3:
                player.score += 500 * player.level;
                lineClearSFX.play(1.0f);
                player.linesCleared += 3;
                break;
            case 4:
                player.score += 800 * player.level;
                tetrisSFX.play(1.0f);
                player.linesCleared += 4;
                break;
            default:
                return;
        }

        for (int row : rowList.items) {
            for (int x = 0; x < p1.COLS; x++) {
                player.board[row][x].setColor(Color.BLACK);
                player.board[row][x].setAvailability(true);
            }
        }
    }

    private void dropAfterClear(PlayerData player, final IntArray rowList) {
        rowList.sort();
        for (int row : rowList.items) {
            for (int i = row; i > 0; i--) {
                for (int j = 0; j < player.COLS; j++) {
                    player.board[i][j].setColor(player.board[i - 1][j].getColor());
                    player.board[i][j].setAvailability(player.board[i - 1][j].isAvailable());
                }
            }
        }
        checkLoss(player);
    }

    private void checkLoss(PlayerData player) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < player.COLS; j++) {
                if (!player.board[i][j].isAvailable()) {
                    player.lost = true;
                    victory1Music.stop();
                    this.pause();
                    this.hide();
                    game.setScreen(new LossScreen(this.game, player));
                    this.dispose();
                    return;
                }
            }
        }
    }


    @Override
    public void show() {
        if (numPlayers == 2) {
            Gdx.graphics.setWindowedMode(650*2, Gdx.graphics.getHeight());
            game.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.camera.update();
            game.batch.setProjectionMatrix(game.camera.combined);
        }
    }

    @Override
    public void resize(final int width, final int height) {
        game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

 class PlayerData{
    int score;
    int level;
    int linesCleared;
    Square[][] board;
    Piece currentPiece;
    float timeControls;
    float timeMovement;
    HeldBlock heldBlock;
    NextBlock nextBlock;
    boolean swapUsed;
    boolean lost;
    int playerNumber;


    static final int ROWS = 22;
    static final int COLS = 10;

    private int horizontalOffset;

    public PlayerData(int playerNum){
        playerNumber = playerNum;
        score = 0;
        level = 1;
        linesCleared = 0;
        board = new Square[ROWS][COLS];
        if(playerNumber == 1){
            horizontalOffset = 0;
        }
        if(playerNumber == 2){
            horizontalOffset = 16;
        }
        //initialize board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j + horizontalOffset, Color.BLACK);
            }
        }

        currentPiece = new Piece();
        heldBlock = new HeldBlock(horizontalOffset);
        nextBlock = new NextBlock(horizontalOffset);
        swapUsed = false;
    }
}

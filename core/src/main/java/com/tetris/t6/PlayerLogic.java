package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import java.awt.*;

/**
 * The type Player data.
 */
public class PlayerLogic {
    /**
     * The Score.
     */
    private int score;
    /**
     * The Level.
     */
    private int level;
    /**
     * Speeds for pieces to fall for different levels.
     * Cells per frame is 1/speed.
     */
    private final float[] levelSpeeds = {0.016_67f, 0.021_017f, 0.026_977f,
        0.035_256f, 0.046_93f, 0.063_61f, 0.087_9f, 0.123_6f, 0.177_5f, 0.259_8f};
    /**
     * Number of lines required to be cleared in order to level up.
     */
    private static final int LINESTOLEVELUP = 10;
    /**
     * The Lines cleared.
     */
    private int linesCleared;
    /**
     * The Board.
     */
    private Square[][] board;
    /**
     * The Current piece.
     */
    private Piece currentPiece;
    /**
     * The Time controls.
     */
    private float timeControls;
    /**
     * The Time movement.
     */
    private float timeMovement;
    /**
     * The Held block.
     */
    private final HeldBlock heldBlock;
    /**
     * The Next block.
     */
    private final NextBlock nextBlock;
    /**
     * The Swap used.
     */
    private boolean swapUsed;
    /**
     * The Player number.
     */
    private final int playerNumber;
    /**
     * The Rows.
     */
    public static final int ROWS = 22;
    /**
     * The Cols.
     */
    public static final int COLS = 10;

    private int horizontalOffset;

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
     * Instantiates a new Player data.
     *
     * @param playerNum the player num
     */
    PlayerLogic(final int playerNum) {
        playerNumber = playerNum;
        score = 0;
        level = 1;
        linesCleared = 0;
        board = new Square[ROWS][COLS];
        if (playerNumber == 1) {
            horizontalOffset = 0;
        }
        if (playerNumber == 2) {
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

        //Loading Sounds
        lockSFX = Gdx.audio.newSound(Gdx.files.internal("lock.wav"));
        rotateSFX = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        lineClearSFX = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetrisSFX = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));
    }

    /**
     * Checks if it's possible to move currentPiece down.
     * If so, calls drawPiece and updates coordinates. If not, calls
     * lockSquares and generates a new piece.
     *
     */
    public void moveDownLogically() {
        timeMovement += levelSpeeds[level];
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
     *
     * @return true if all four squares below the piece are available
     */
    private boolean moveDownPossible() {
        final Point[][] dimensions = currentPiece.getDimensions();
        final int row = currentPiece.getRow();
        final int col = currentPiece.getCol();
        final int rotationNum = currentPiece.getRotationNum();
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
    public void hardDrop() {
        while (moveDownPossible()) {
            moveDownLogically();
        }
    }

    /**
     * Move left or right depending on parameter.
     *
     * @param leftRight -1 to move left, 1 for right.
     */
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

    /**
     * Checks if possible to move all four squares of currentPiece left/right.
     *
     * @param leftRight -1 for left, 1 for right
     * @return true or false depending on availability of all four squares.
     */
    private boolean moveLeftRightPossible(final int leftRight) {
        final Point[][] dimensions = currentPiece.getDimensions();
        final int row = currentPiece.getRow();
        final int col = currentPiece.getCol();
        final int rotationNum = currentPiece.getRotationNum();
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
     *
     * @param rotationNum To access appropriate rotation state from 2D array.
     * @return true if all four squares are available to rotate into.
     */
    private boolean rotationPossible(final int rotationNum) {
        final Point[][] dimensions = currentPiece.getDimensions();
        final int row = currentPiece.getRow();
        final int col = currentPiece.getCol();
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
        final IntArray rowsToCheck = new IntArray(4);

        final Point[][] dimensions = currentPiece.getDimensions();
        final int row = currentPiece.getRow();
        final int col = currentPiece.getCol();
        final int rotationNum = currentPiece.getRotationNum();
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
     * If so, calls clearLine() for those p1.ROWS. Otherwise calls checkLoss().
     *
     * @param rowList List of p1.ROWS to check.
     */
    private void checkFullRow(final IntArray rowList) {
        final IntArray fullRows = new IntArray();
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
    public void levelUp() {
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

        for (final int row : rowList.items) {
            for (int x = 0; x < COLS; x++) {
                board[row][x].setColor(Color.BLACK);
                board[row][x].setAvailability(true);
            }
        }
    }

    private void dropAfterClear(final IntArray rowList) {
        rowList.sort();
        for (final int row : rowList.items) {
            for (int i = row; i > 0; i--) {
                for (int j = 0; j < COLS; j++) {
                    board[i][j].setColor(board[i - 1][j].getColor());
                    board[i][j].setAvailability(board[i - 1][j].isAvailable());
                }
            }
        }
    }

    public boolean checkLoss() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].isAvailable()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Draw the four Squares of a piece based on current state.
     * @param color  the color
     */
    public void drawPiece(final Color color) {
        //row and column for the top-left corner
        final int row = currentPiece.getRow();
        final int col = currentPiece.getCol();
        final int rNum = currentPiece.getRotationNum();
        final Point[][] dimensions = currentPiece.getDimensions();

        for (int i = 0; i < 4; i++) {
            final int squareRow = row + dimensions[rNum][i].x;
            final int squareCol = col + dimensions[rNum][i].y;
            board[squareRow][squareCol].setColor(color);
        }
    }

    public HeldBlock getHeldBlock() {
        return heldBlock;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public NextBlock getNextBlock() {
        return nextBlock;
    }

    public void setCurrentPiece(final Piece p) {
        currentPiece = p;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }
    public void setSwapUsed(boolean b) {
        swapUsed = b;
    }

    public boolean getSwapUsed() {
        return swapUsed;
    }

    public Square[][] getBoard() {
        return board;
    }

    public float getTimeControls() {
        return timeControls;
    }

    public void setTimeControls(float t) {
        timeControls = t;
    }

    public void setTimeMovement(float t) {
        timeMovement = t;
    }
}

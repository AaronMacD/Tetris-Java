package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import java.awt.Point;

/**
 * The type Player data.
 */
public final class PlayerLogic {
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
    private final float[] levelSpeeds =
        {0.016_67f, 0.021_017f, 0.026_977f, 0.035_256f, 0.046_93f, 0.063_61f,
            0.087_9f, 0.123_6f, 0.177_5f, 0.259_8f};
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
     * Number of rows in the board.
     */
    public static final int ROWS = 22;
    /**
     * Number of columns on the board.
     */
    public static final int COLS = 10;
    /**
     * Columns to shift over player 2 graphics in multiplayer.
     */
    private int horizontalOffset;
    /**
     * Object for accessing sound effects.
     */
    private SoundManager sfx;

    /**
     * Instantiates a new Player.
     *
     * @param playerNum 1 for player 1, 2 for player 2
     */
    public PlayerLogic(final int playerNum) {
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

        sfx = new SoundManager();
        currentPiece = new Piece();
        heldBlock = new HeldBlock(horizontalOffset);
        nextBlock = new NextBlock(horizontalOffset);
        swapUsed = false;
    }

    /**
     * Checks if it's possible to move currentPiece down.
     * If so, calls drawPiece and updates coordinates. If not, calls
     * lockSquares and generates a new piece.
     *
     */
    public void moveDownLogically() {
        timeMovement += levelSpeeds[level];
        //>=1 is default, but can be adjusted to reduce difficulty.
        if (timeMovement >= 2.5) {
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
        if (!(leftRight == -1 || leftRight == 1)) {
            throw new IllegalArgumentException("Parameter must be -1 or 1");
        }

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
        if (!(direction == -1 || direction == 1)) {
            throw new IllegalArgumentException("Direction must be -1 or 1");
        }
        int rotationNum = currentPiece.getRotationNum();
        //determine the rotation state after rotating clockwise (1)
        //or counterclockwise (-1)
        rotationNum = Math.floorMod(rotationNum + direction, 4);

        if (rotationPossible(rotationNum)) {
            drawPiece(Color.BLACK);
            currentPiece.setRotationNum(rotationNum);
           sfx.playRotate();
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
     * @param rowList List of ROWS to check.
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
            sfx.playLock();
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
                sfx.playLineClear();
                linesCleared += 1;
                break;
            case 2:
                score += 300 * level;
                sfx.playLineClear();
                linesCleared += 2;
                break;
            case 3:
                score += 500 * level;
                sfx.playLineClear();
                linesCleared += 3;
                break;
            case 4:
                score += 800 * level;
                sfx.playTetris();
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

    /**
     * Checks to see if the player has lost.
     * @return boolean indicating if the player has lost.
     */
    public boolean checkLoss() {
        boolean lost = false;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].isAvailable()) {
                    lost = true;
                }
            }
        }

        return lost;
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

    /**
     * Gets the board 2D array.
     * @return the board.
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Gets the current piece.
     * @return the current piece.
     */
    public Piece getCurrentPiece() {
        return currentPiece;
    }
    /**
     * Gets the held block.
     * @return the held block.
     */
    public HeldBlock getHeldBlock() {
        return heldBlock;
    }
    /**
     * Gets the player's level.
     * @return the player's level.
     */
    public int getLevel() {
        return level;
    }
    /**
     * Gets the number of lines cleared.
     * @return the number of lines cleared.
     */
    public int getLinesCleared() {
        return linesCleared;
    }
    /**
     * Gets the block that is in the "next" slot.
     * @return the block that is in the "next" slot.
     */
    public NextBlock getNextBlock() {
        return nextBlock;
    }
    /**
     * Gets the player's score.
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the "swapUsed" boolean.
     * @return the "swapUsed" boolean.
     */
    public boolean isSwapUsed() {
        return swapUsed;
    }

    /**
     * Gets the "timeControls" variable.
     * @return the "timeControls" variable.
     */
    public float getTimeControls() {
        return timeControls;
    }

    /**
     * Sets the current piece.
     * @param p the new currentPiece.
     */
    public void setCurrentPiece(final Piece p) {
        currentPiece = p;
    }
    /**
     * Sets the swapUsed boolean.
     * @param b the true or false value.
     */
    public void setSwapUsed(final boolean b) {
        swapUsed = b;
    }

    /**
     * Sets the timeControls variable.
     * @param t the time value.
     */
    public void setTimeControls(final float t) {
        timeControls = t;
    }

    /**
     * Sets the timeMovement variable.
     * @param t the time value.
     */
    public void setTimeMovement(final float t) {
        timeMovement = t;
    }
}

package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

/**
 * Represents the block in the hold slot.
 */
public class HeldBlock {
    /**
     * The starting column of displayArea to draw the piece.
     */
    private static final int COL = 11;
    /**
     * The starting row of displayArea to draw the piece.
     */
    private static final int ROW = 8;

    private int horizontalOffset;

    /**
     * Width of the piece grid in squares.
     */
    private static final int WIDTHSQUARES = 4;

    /**
     * Height of the piece grid in squares.
     */
    private static final int HEIGHTSQUARES = 4;
    /**
     * The piece being held.
     */
    private Piece heldPiece;
    /**
     * 2D array of Squares to display held block.
     */
    private Square[][] displayArea;

    /**
     * Constructor for held block. Instantiates displayArea with black Squares.
     */
    public HeldBlock(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;

        heldPiece = null;
        displayArea = new Square[HEIGHTSQUARES][WIDTHSQUARES];
        for (int i = 0; i < HEIGHTSQUARES; i++) {
            for (int j = 0; j < WIDTHSQUARES; j++) {
                displayArea[i][j] = new Square(i + ROW, j + COL + horizontalOffset, Color.BLACK);
            }
        }
    }

    /**
     * Sets the held piece and calls updateGrid to change colors.
     * @param newPiece the newly-held piece.
     */
    public void setHeldPiece(Piece newPiece) {
        heldPiece = newPiece;
        updateGrid(heldPiece.getColor());
    }

    /**
     * Gets the held piece.
     * @return heldPiece.
     */
    public Piece getHeldPiece() {
        return heldPiece;
    }


    public Piece swapPiece(Piece newPiece) {
        updateGrid(Color.BLACK);
        Piece temp = heldPiece;
        temp.setRow(1);
        temp.setCol(0);
        heldPiece = newPiece;
        updateGrid(heldPiece.getColor());
        return temp;
    }


    private void updateGrid(Color color) {
        Point[][] dimensions = heldPiece.getDimensions();
        for (int i = 0; i < 4; i++) {
            int squareRow = dimensions[heldPiece.getRotationNum()][i].x;
            int squareCol = dimensions[heldPiece.getRotationNum()][i].y;
            displayArea[squareRow][squareCol].setColor(color);
        }
    }
    public void drawNext(ShapeDrawer draw) {
        for (int i = 0; i < HEIGHTSQUARES; i++) {
            for (int j = 0; j < WIDTHSQUARES; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}


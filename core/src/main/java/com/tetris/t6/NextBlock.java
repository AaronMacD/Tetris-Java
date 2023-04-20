package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

/**
 * The type Next block.
 */
public final class NextBlock {
    /**
     * Column of the screen the NextBlock grid starts in.
     */
    private static final int COL = 11;
    /**
     * Row of the screen the NextBlock grid starts in.
     */
    private static final int ROW = 2;

    /**
     * Width in squares of the NextBlock grid.
     */
    private static final int WIDTHSQUARES = 4;
    /**
     * Height in squares of the NextBlock grid.
     */
    private static final int HEIGHTSQUARES = 4;
    /**
     * The piece stored in the NextBlock grid.
     */
    private Piece nextPiece;
    /**
     * The array of Squares representing the NextBlock grid.
     */
    private Square[][] displayArea;

    /**
     * Instantiates a new Next block.
     *
     * @param horizontalOffset the horizontal offset
     */
//Constructor
    public NextBlock(final int horizontalOffset) {
        if (!(horizontalOffset == 0 || horizontalOffset == 16)) {
            throw new IllegalArgumentException("Horizontal offset must be "
                + "0 or 16");
        }

        displayArea = new Square[WIDTHSQUARES][HEIGHTSQUARES];
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j] = new Square(i + ROW,
                    j + COL + horizontalOffset, Color.BLACK);
            }
        }
        generateNextPiece();
    }

    /**
     * Gets next piece.
     *
     * @return the next piece
     */
    public Piece getNextPiece() {
        updateGrid(Color.BLACK);
        return nextPiece;
    }

    /**
     * Generate next piece.
     */
    public void generateNextPiece() {
        nextPiece = new Piece();
        updateGrid(nextPiece.getColor());
    }

    private void updateGrid(final Color color) {
        final Point[][] dimensions = nextPiece.getDimensions();
        for (int i = 0; i < WIDTHSQUARES; i++) {
            final int squareRow = dimensions[0][i].x;
            final int squareCol = dimensions[0][i].y;
            displayArea[squareRow][squareCol].setColor(color);
        }
    }

    /**
     * Draw next piece.
     *
     * @param draw ShapeDrawer object
     */
    public void drawNext(final ShapeDrawer draw) {
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}

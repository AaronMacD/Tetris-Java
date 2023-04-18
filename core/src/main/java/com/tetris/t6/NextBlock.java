package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

/**
 * The type Next block.
 */
public final class NextBlock {

    private static int col = 11;
    private static int row = 2;

    private static final int WIDTHSQUARES = 4;
    private static final int HEIGHTSQUARES = 4;
    private Piece nextPiece;
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
                displayArea[i][j] = new Square(i + row,
                    j + col + horizontalOffset, Color.BLACK);
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
        nextPiece.setCol(0);
        nextPiece.setRow(1);
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
        for (int i = 0; i < 4; i++) {
            final int squareRow = dimensions[0][i].x;
            final int squareCol = dimensions[0][i].y;
            displayArea[squareRow][squareCol].setColor(color);
        }
    }

    /**
     * Draw next piece.
     *
     * @param draw the draw
     */
    public void drawNext(final ShapeDrawer draw) {
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}

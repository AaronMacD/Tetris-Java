package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

public final class NextBlock {

    private static int col = 11;
    private static int row = 2;
    private int horizontalOffset;
    private final static int WIDTHSQUARES = 4;
    private final static int HEIGHTSQUARES = 4;
    private Piece nextPiece;
    private Square[][] displayArea;

    //Constructor
    public NextBlock(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
        displayArea = new Square[WIDTHSQUARES][HEIGHTSQUARES];
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j] = new Square(i + row, j + col + horizontalOffset,
                    Color.BLACK);
            }
        }
        generateNextPiece();
    }

    public Piece getNextPiece() {
        nextPiece.setCol(0);
        nextPiece.setRow(1);
        updateGrid(Color.BLACK);
        return nextPiece;
    }

    public void generateNextPiece() {
        nextPiece = new Piece();
        nextPiece.setCol(col);
        nextPiece.setRow(row);
        updateGrid(nextPiece.getColor());
    }

    private void updateGrid(final Color color) {
        Point[][] dimensions = nextPiece.getDimensions();
        for (int i = 0; i < 4; i++) {
            int squareRow = dimensions[0][i].x;
            int squareCol = dimensions[0][i].y;
            displayArea[squareRow][squareCol].setColor(color);
        }
    }
    public void drawNext(final ShapeDrawer draw) {
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}

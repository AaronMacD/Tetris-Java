package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

public final class NextBlock {

    private int col = 12;
    private int row = 5;
    private final int WIDTH_SQUARES = 4;
    private final int HEIGHT_SQUARES = 4;
    private Piece nextPiece;
    private Square[][] displayArea;

    //Constructor
    //TODO Figure out the parameters we're going to be passing to the constructor
    public NextBlock() {
        displayArea = new Square[WIDTH_SQUARES][HEIGHT_SQUARES];
        for (int i = 0; i < WIDTH_SQUARES; i++) {
            for (int j = 0; j < HEIGHT_SQUARES; j++) {
                displayArea[i][j] = new Square(i + row, j + col,
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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}

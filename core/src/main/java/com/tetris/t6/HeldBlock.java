package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.Point;

public class HeldBlock {
    private static int col = 11;
    private static int row = 8;

    /**
     * Width of the piece in squares.
     */
    private final static int WIDTHSQUARES = 4;

    /**
     * Height of the piece in squares.
     */
    private final static int HEIGHTSQUARES = 4;
    private Piece heldPiece;
    private Square[][] displayArea;




    //Constructor
    public HeldBlock() {
        heldPiece = null;
        displayArea = new Square[WIDTHSQUARES][HEIGHTSQUARES];
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j] = new Square(i + row, j + col, Color.BLACK);
            }
        }
    }

    public void setHeldPiece(Piece newPiece) {
        heldPiece = newPiece;
        updateGrid(heldPiece.getColor());
    }
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
        for (int i = 0; i < WIDTHSQUARES; i++) {
            for (int j = 0; j < HEIGHTSQUARES; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}


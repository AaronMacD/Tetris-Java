package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.*;

public class HeldBlock{

    private int col = 12;
    private int row = 15;
    private Piece heldPiece;
    private Square[][] displayArea;




    //Constructor
    public HeldBlock(){
        heldPiece = null;
        displayArea = new Square[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++){
                displayArea[i][j] = new Square(i+row, j+col, Color.BLACK);
            }
        }
    }

    public void setHeldPiece(Piece newPiece){
        heldPiece = newPiece;
        updateGrid(heldPiece.getColor());
    }
    public Piece getHeldPiece(){
        return heldPiece;
    }
    public Piece swapPiece(Piece newPiece){
        updateGrid(Color.BLACK);
        Piece temp = heldPiece;
        temp.setRow(0);
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
    public void drawNext(ShapeDrawer draw){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                displayArea[i][j].drawSquare(draw);
            }
        }
    }
}


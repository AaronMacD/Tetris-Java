package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import java.util.ArrayList;
import java.util.Random;

import static com.tetris.t6.Orientation.*;

public class Piece {
    //3d array because we need to create 2d shapes that have multiple different rotations
    private int dimensions[][][];

    //can be 0, 1, 2, or 3
    //TODO consider making this an enum
    private int rotationNum;

    //x-coordinate of the top-left corner of a piece
    private int row;
    private int col;
    private Orientation orientation;

    private Color color;
    private BlockShape blockShape;

    Piece() {
        row = 4;
        col = 4;
        rotationNum = 0;
        orientation = Orientation.UP;
        generatePieceType();
    }

    public int getRow() { return this.row; }
    public int getCol() { return this.col; }
    public Color getColor() { return this.color; }
    public int[][][] getDimensions() {
        return dimensions;
    }
    public int getRotationNum() { return this.rotationNum; }
    public void moveLeft(){

    }

    public void moveRight(){

    }

    /**
     *
     * rNum  the current rotation number: 0, 1, 2, or 3
     * direction 1 if clockwise, -1 if counterclockwise
     */

    //TODO Change to orientation enum?
//    public void rotate(int rNum, int direction){
//        IntArray coords = new IntArray();
//
//        rNum = Math.floorMod(rNum + direction, 4);
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (dimensions[rNum][i][j] == 1) {
//                    if (coordEmpty(xCoord+i,yCoord-j)) {
//                        coords.add(xCoord+i,yCoord-j);
//                    }
//                }
//            }
//        }
//
//        //if rotation is succesful, draw piece and set rotationNum
//        if (coords.size == 4) {
//            drawPiece(coords, this.blockShape, this.orientation);
//            rotationNum = rNum;
//        }
//    }

    private boolean coordEmpty(int x, int y) {
        return true;
    }

    private void generatePieceType() {
//        Random rand = new Random();
//        int num = rand.nextInt(7);
        int num = 3;

        //create new piece based on random number
        switch (num) {
            case 0: makeJ();
                break;
            case 1: makeL();
                break;
            case 2: makeLine();
                break;
            case 3: makeS();
                break;
            case 4: makeSquare();
                break;
            case 5: makeT();
                break;
            default: makeZ();
                break;
        }
    }

    private void makeJ() {
        dimensions = new int[][][] {
            //rotation 0
             {{1,0,0,0},
              {1,1,1,0},
              {0,0,0,0},
              {0,0,0,0}},
            //rotation 1
             {{0,1,1,0},
              {0,1,0,0},
              {0,1,0,0},
              {0,0,0,0}},
            //rotation 2
             {{0,0,0,0},
              {1,1,1,0},
              {0,0,1,0},
              {0,0,0,0}},
            //rotation 3
             {{0,1,0,0},
              {0,1,0,0},
              {1,1,0,0},
              {0,0,0,0}}
        };

        color = new Color(Color.BLUE);
        this.blockShape = BlockShape.J;

    }
    private void makeL() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,0,1,0},
             {1,1,1,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{0,1,0,0},
             {0,1,0,0},
             {0,1,1,0},
             {0,0,0,0}},
            //rotation 2
            {{0,0,0,0},
             {1,1,1,0},
             {1,0,0,0},
             {0,0,0,0}},
            //rotation 3
            {{1,1,0,0},
             {0,1,0,0},
             {0,1,0,0},
             {0,0,0,0}}
        };

        color = new Color(Color.ORANGE);
        this.blockShape = BlockShape.L;
    }

    private void makeLine() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,0,0,0},
             {1,1,1,1},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{0,0,1,0},
             {0,0,1,0},
             {0,0,1,0},
             {0,0,1,0}},
            //rotation 2
            {{0,0,0,0},
             {0,0,0,0},
             {1,1,1,1},
             {0,0,0,0}},
            //rotation 3
            {{0,1,0,0},
             {0,1,0,0},
             {0,1,0,0},
             {0,1,0,0}}
        };

        color = new Color(Color.CYAN);
        this.blockShape = BlockShape.LINE;
    }

    private void makeS() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,1,1,0},
             {1,1,0,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{0,1,0,0},
             {0,1,1,0},
             {0,0,1,0},
             {0,0,0,0}},
            //rotation 2
            {{0,0,0,0},
             {0,1,1,0},
             {1,1,0,0},
             {0,0,0,0}},
            //rotation 3
            {{1,0,0,0},
             {1,1,0,0},
             {0,1,0,0},
             {0,0,0,0}}
        };

        color = new Color(Color.GREEN);
        this.blockShape = BlockShape.S;
    }

    private void makeSquare() {
        //still represented as 4x4x4 array for convenience
        dimensions = new int[][][] {
            //rotation 0
            {{1,1,0,0},
             {1,1,0,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{1,1,0,0},
             {1,1,0,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 2
            {{1,1,0,0},
             {1,1,0,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 3
            {{1,1,0,0},
             {1,1,0,0},
             {0,0,0,0},
             {0,0,0,0}},
        };

        color = new Color(Color.YELLOW);
        this.blockShape = BlockShape.SQUARE;
    }

    private void makeT() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,1,0,0},
             {1,1,1,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{0,1,0,0},
             {0,1,1,0},
             {0,1,0,0},
             {0,0,0,0}},
            //rotation 2
            {{0,0,0,0},
             {1,1,1,0},
             {0,1,0,0},
             {0,0,0,0}},
            //rotation 3
            {{0,1,0,0},
             {1,1,0,0},
             {0,1,0,0},
             {0,0,0,0}}
        };

        color = new Color(Color.PURPLE);
        this.blockShape = BlockShape.T;
    }

    private void makeZ() {
        dimensions = new int[][][] {
            //rotation 0
            {{1,1,0,0},
             {0,1,1,0},
             {0,0,0,0},
             {0,0,0,0}},
            //rotation 1
            {{0,0,1,0},
             {0,1,1,0},
             {0,1,0,0},
             {0,0,0,0}},
            //rotation 2
            {{0,0,0,0},
             {1,1,0,0},
             {0,1,1,0},
             {0,0,0,0}},
            //rotation 3
            {{0,1,0,0},
             {1,1,0,0},
             {1,0,0,0},
             {0,0,0,0}}
        };

        color = new Color(Color.RED);
        this.blockShape = BlockShape.Z;
    }
}

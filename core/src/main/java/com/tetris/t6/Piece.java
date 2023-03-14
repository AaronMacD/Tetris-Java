package com.tetris.t6;

import java.util.Random;

public class Piece {
    //3d array because we need to create 2d shapes that have multiple different rotations
    private int dimensions[][][];

    //can be 0, 1, 2, or 3
    //TODO consider making this an enum
    private int rotationNum;

    //x-coordinate of the top-left corner of a piece's
    private int xCoord;
    private int yCoord;
    private Orientation orientation;

    Piece() {

    }

    public int getxCoord(){
        return this.xCoord;
    }
    public void setxCoord(int x){
        this.xCoord = x;
    }

    public int getyCoord(){
        return this.yCoord;
    }
    public void setyCoord(int y){
        this.yCoord = y;
    }


    public void moveLeft(){

    }

    public void moveRight(){

    }

    public void rotateCW(){

    }

    public void rotateCCW(){

    }

    private void generatePieceType() {
        Random rand = new Random();
        int num = rand.nextInt(7);

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

    private void draw3x3(int rotationNum) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (dimensions[rotationNum][i][j] == 1) {
                    if (coordEmpty(xCoord+i,yCoord+j)) {
                        //add coords to array
                    }
                }
            }
        }
    }

    private void drawLine() {

    }

    private void drawSquare() {

    }
    private void makeJ() {
        dimensions = new int[][][] {
            //rotation 0
             {{1, 0, 0},
              {1, 1, 1},
              {0, 0, 0}},
            //rotation 1
             {{0, 1, 1},
              {0, 1, 0},
              {0, 1, 0}},
            //rotation 2
             {{0, 0, 0},
              {1, 1, 1},
              {0, 0, 1}},
            //rotation 3
             {{0, 1, 0},
              {0, 1, 0},
              {1, 1, 0}}
        };
    }
    private void makeL() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,0,1},
             {1,1,1},
             {0,0,0}},
            //rotation 1
            {{0,1,0},
             {0,1,0},
             {0,1,1}},
            //rotation 2
            {{0,0,0},
             {1,1,1},
             {1,0,0}},
            //rotation 3
            {{1,1,0},
             {0,1,0},
             {0,1,0}}
        };
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
    }

    private void makeS() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,1,1},
             {1,1,0},
             {0,0,0}},
            //rotation 1
            {{0,1,0},
             {0,1,1},
             {0,0,1}},
            //rotation 2
            {{0,0,0},
             {0,1,1},
             {1,1,0}},
            //rotation 3
            {{1,0,0},
             {1,1,0},
             {0,1,0}}
        };
    }

    private void makeSquare() {
        //still represented as 3D array for convenience
        dimensions = new int[][][] {
            //rotation 0
            {{1,1},
             {1,1}},
            //rotation 1
            {{1,1},
             {1,1}},
            //rotation 2
            {{1,1},
             {1,1}},
            //rotation 3
            {{1,1},
             {1,1}}
        };
    }

    private void makeT() {
        dimensions = new int[][][] {
            //rotation 0
            {{0,1,0},
             {1,1,1},
             {0,0,0}},
            //rotation 1
            {{0,1,0},
             {0,1,1},
             {0,1,0}},
            //rotation 2
            {{0,0,0},
             {1,1,1},
             {0,1,0}},
            //rotation 3
            {{0,1,0},
             {1,1,0},
             {0,1,0}}
        };
    }

    private void makeZ() {
        dimensions = new int[][][] {
            //rotation 0
            {{1,1,0},
             {0,1,1},
             {0,0,0}},
            //rotation 1
            {{0,0,1},
             {0,1,1},
             {0,1,0}},
            //rotation 2
            {{0,0,0},
             {1,1,0},
             {0,1,1}},
            //rotation 3
            {{0,1,0},
             {1,1,0},
             {1,0,0}}
        };
    }
}

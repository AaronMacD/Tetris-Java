package com.tetris.t6;

import com.sun.org.apache.xpath.internal.operations.Or;

public abstract class Pieces {

    private int xCoord;
    private int yCoord;
    private Orientation orientation;


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
}

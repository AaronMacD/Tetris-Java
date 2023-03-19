package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square {

    private int x;
    private int y;
    private Color color;

    public Square(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public Color getColor() { return color; }

//    public void drawSquare(ShapeRenderer shape){
//
//     /*
//     If the gridWidth and gridHeight don't change over time then you can
//     move the calculation of the left, right, bottom and top positions
//     into the constructor for better performance.
//     */
//
//
//        shape.rect(x, y, x * squareWidth, y * squareHeight);
//    }
}

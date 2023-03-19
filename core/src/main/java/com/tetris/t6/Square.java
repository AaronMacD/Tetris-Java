package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square {

    private int x;
    private int y;
    private Color color;

    private final int width = 40;
    private final int height = 40;

    public Square(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public Color getColor() { return color; }

    public void drawSquare(ShapeRenderer shape){
        shape.rect(x * width, y * height, width, height);
        shape.setColor(color);
    }
}

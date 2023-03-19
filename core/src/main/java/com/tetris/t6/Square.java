package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square {

    private int row;
    private int col;
    private Color color;

    private final int width = 40;
    private final int height = 40;

    public Square(int row, int col, Color color){
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public Color getColor() { return color; }

    public void drawSquare(ShapeRenderer shape){
        shape.rect(col * width, row * height, width, height);
        shape.setColor(color);
    }
}

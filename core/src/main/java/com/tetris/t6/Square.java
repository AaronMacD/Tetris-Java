package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Square {

    private int row;
    private int col;
    private Color color;
    //whether square is available
    boolean availability;

    private final int width = 40;
    private final int height = 40;

    private int x;

    private int y;

    public Square(int row, int col, Color color){
        this.row = row;
        this.col = col;
        this.color = color;
        availability = true;

        x = col * width;

        //converts to y-down coordinates
        y = Gdx.graphics.getHeight() - (row * height);

    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public Color getColor() { return color; }

    public void setColor(Color color){
        this.color = color;
    }

    public boolean isAvailable() { return availability; }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void drawSquare(ShapeDrawer shape) {
        shape.filledRectangle(x, y, width, height);
        shape.setColor(color);
    }
}

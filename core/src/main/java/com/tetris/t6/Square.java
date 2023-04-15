package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

public final class Square {

    private int row;
    private int col;
    private Color color;
    //whether square is available
    private boolean availability;

    private final int width = 40;
    private final int height = 40;

    private int x;

    private int y;

    public Square(final int aRow, final int aCol, final Color aColor) {
        this.row = aRow;
        this.col = aCol;
        this.color = aColor;
        availability = true;

        x = 20 + col * width;

        //converts to y-down coordinates
        y = 800 - (row * height);

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color aColor) {
        this.color = aColor;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(final boolean anAvailability) {
        this.availability = anAvailability;
    }

    public void drawSquare(final ShapeDrawer shape) {
        shape.setColor(color);
        shape.filledRectangle(x, y, width - 1, height - 1);

    }
}

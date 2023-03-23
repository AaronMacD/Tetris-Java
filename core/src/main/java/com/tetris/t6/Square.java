package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * The type Square.
 */
public class Square {

    private int row;
    private int col;
    private Color color;
    /**
     * The Availability.
     */
//whether square is available
    boolean availability;

    private final int width = 40;
    private final int height = 40;

    private int x;

    private int y;

    /**
     * Instantiates a new Square.
     *
     * @param row   the row
     * @param col   the col
     * @param color the color
     */
    public Square(int row, int col, Color color){
        this.row = row;
        this.col = col;
        this.color = color;
        availability = true;

        x = col * width;

        //converts to y-down coordinates
        y = Gdx.graphics.getHeight() - (row * height);

    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() { return row; }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() { return col; }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() { return color; }

    /**
     * Set color.
     *
     * @param color the color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() { return availability; }

    /**
     * Sets availability.
     *
     * @param availability the availability
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    /**
     * Draw square.
     *
     * @param shape the shape
     */
    public void drawSquare(ShapeDrawer shape) {
        shape.filledRectangle(x, y, width, height);
        shape.setColor(color);
    }
}

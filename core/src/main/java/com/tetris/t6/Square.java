package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Represents a single square on the board.
 */
public final class Square {
    /**
     * Row of Square.
     */
    private int row;
    /**
     * Column of Square.
     */
    private int col;
    /**
     * Color of Square.
     */
    private Color color;
    /**
     * Boolean for whether the square is available.
     */
    private boolean availability;
    /**
     * Width of a Square in pixels.
     */
    private static final int WIDTH = 40;
    /**
     * Height of a Square in pixels.
     */
    private static final int HEIGHT = 40;
    /**
     * x coordinates.
     */
    private int x;
    /**
     * y coordinates.
     */
    private int y;

    /**
     * Creates a square object, that is used to fill the board.
     *
     * @param aRow   sets the square's row.
     * @param aCol   sets the square's col.
     * @param aColor sets the square's color.
     */
    public Square(final int aRow, final int aCol, final Color aColor) {
        if (aRow < 0 || aRow > 22 || aCol < 0 || aCol > 10) {
            throw new IllegalArgumentException("Invalid row or column");
        }
        this.row = aRow;
        this.col = aCol;
        this.color = aColor;
        availability = true;

        x = 10 + col * WIDTH;

        //converts to y-down coordinates
        y = 805 - (row * HEIGHT);
    }

    /**
     * Gets color.
     *
     * @return the current color of sqaure.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param aColor sets the color of the square.
     */
    public void setColor(final Color aColor) {
        this.color = aColor;
    }

    /**
     * Is available boolean.
     *
     * @return if the square is availability or not.
     */

    public boolean isAvailable() {
        return availability;
    }

    /**
     * Sets availability.
     *
     * @param anAvailability sets the availability of the square.
     */
    public void setAvailability(final boolean anAvailability) {
        this.availability = anAvailability;
    }

    /**
     * Draw square.
     *
     * @param shape draws the shape onto the board.
     */
    public void drawSquare(final ShapeDrawer shape) {
        shape.setColor(color);
        shape.filledRectangle(x, y, WIDTH - 1, HEIGHT - 1);

    }
}

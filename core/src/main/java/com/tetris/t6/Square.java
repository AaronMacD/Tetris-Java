package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Square Class
 */
public final class Square {
    /**
     * Row(s) of Square.
     */
    private int row;
    /**
     * Col(s) of Square.
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
     * Static Width.
     */
    private final static int WIDTH = 40;
    /**
     * Static Height.
     */
    private final static int HEIGHT = 40;
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
     * @param aRow sets the square's row.
     * @param aCol sets the square's col.
     * @param aColor sets the square's color.
     */
    public Square(final int aRow, final int aCol, final Color aColor) {
        this.row = aRow;
        this.col = aCol;
        this.color = aColor;
        availability = true;

        x = 10 + col * WIDTH;

        //converts to y-down coordinates
        y = 805 - (row * HEIGHT);

    }
    /**
     * @return the current row of the square.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the current col of the square.
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the current color of sqaure.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param aColor sets the color of the square.
     */

    public void setColor(final Color aColor) {
        this.color = aColor;
    }
    /**
     * @return if the square is availability or not.
     */

    public boolean isAvailable() {
        return availability;
    }

    /**
     * @param anAvailability sets the availability of the square.
     */
    public void setAvailability(final boolean anAvailability) {
        this.availability = anAvailability;
    }

    /**
     * @param shape draws the shape onto the board.
     */
    public void drawSquare(final ShapeDrawer shape) {
        shape.setColor(color);
        shape.filledRectangle(x, y, WIDTH - 1, HEIGHT - 1);

    }
}

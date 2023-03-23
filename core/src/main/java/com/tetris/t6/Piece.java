package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;

import java.awt.*;
import java.util.Random;

/**
 * Represents a single Piece
 */
public class Piece {
    /**
     * 2D array of points. First dimension determines rotation status,
     * second dimension holds Points representing coordinates relative to the top-left
     * square of a piece.
     */
    private Point dimensions[][];

    //can be 0, 1, 2, or 3
    //TODO consider making this an enum
    private int rotationNum;

    //row number of the top-left square of a piece
    private int row;
    //column number of the top-left square of a piece
    private int col;

    private Color color;

    /**
     * Instantiates a new Piece. Initializes row, col, rotationNum,
     * and calls generatePieceType
     */
    Piece() {
        row = 1;
        col = 0;
        rotationNum = 0;
        generatePieceType();
    }

    /**
     * Gets row of the top-left square.
     *
     * @return row
     */
    public int getRow() { return this.row; }

    /**
     * Gets column of the top left square.
     *
     * @return col
     */
    public int getCol() { return this.col; }

    /**
     * Gets color of the piece.
     *
     * @return color
     */
    public Color getColor() { return this.color; }

    /**
     * Get dimensions point [ ] [ ].
     *
     * @return the point [ ] [ ] array of piece coordinates
     */
    public Point[][] getDimensions() {
        return dimensions;
    }

    /**
     * Gets rotation number.
     *
     * @return rotationNum
     */
    public int getRotationNum() { return this.rotationNum; }

    /**
     * Sets row of the top-left square.
     *
     * @param row the row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets column of the top-left square.
     *
     * @param col the col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets rotation number.
     *
     * @param rotationNum the rotation number
     */
    public void setRotationNum(int rotationNum) {
        this.rotationNum = rotationNum;
    }

    /**
     * Move left by one column.
     */
    public void moveLeft(){ col--; }

    /**
     * Move right by one column.
     */
    public void moveRight() { col++; }

    /**
     * Move down by one row.
     */
    public void moveDown() {
        row++;
    }

    /**
     *  Generates a random number and calls a method to set
     *  coordinates for a specific piece type
     */
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

    /**
     * Creates relative coordinates for a J piece and sets color.
     */
    private void makeJ() {
        //Point values are row, col for individual square of a piece
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2)},
            //rotation 1
            {new Point(0,1), new Point(0,2), new Point(1,1), new Point(2,1)},
            //rotation 2
            {new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,2)},
            //rotation 3
            {new Point(0,1), new Point(1,1), new Point(2,0), new Point(2,1)}
        };

        color = new Color(Color.BLUE);
    }

    /**
     * Creates relative coordinates for an L piece and sets color.
     */
    private void makeL() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,2), new Point(1,0), new Point(1,1), new Point(1,2)},
            //rotation 1
            {new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,2)},
            //rotation 2
            {new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,0)},
            //rotation 3
            {new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1)}
        };

        color = new Color(Color.ORANGE);
    }

    /**
     * Creates relative coordinates for a line piece and sets color.
     */
    private void makeLine() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3)},
            //rotation 1
            {new Point(0,2), new Point(1,2), new Point(2,2), new Point(3,2)},
            //rotation 2
            {new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3)},
            //rotation 3
            {new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1)}
        };

        color = new Color(Color.CYAN);
    }

    /**
     * Creates relative coordinates for an S piece and sets color.
     */
    private void makeS() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,1), new Point(0,2), new Point(1,0), new Point(1,1)},
            //rotation 1
            {new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2)},
            //rotation 2
            {new Point(1,1), new Point(1,2), new Point(2,0), new Point(2,1)},
            //rotation 3
            {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)}
        };

        color = new Color(Color.GREEN);
    }

    /**
     * Creates relative coordinates for a Square piece and sets color.
     */
    private void makeSquare() {
        //still represented as 2D array for convenience
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
            //rotation 1
            {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
            //rotation 2
            {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)},
            //rotation 3
            {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1)}
        };

        color = new Color(Color.YELLOW);
    }

    /**
     * Creates relative coordinates for a T piece and sets color.
     */
    private void makeT() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,1), new Point(1,0), new Point(1,1), new Point(1,2)},
            //rotation 1
            {new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,1)},
            //rotation 2
            {new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,1)},
            //rotation 3
            {new Point(0,1), new Point(1,0), new Point(1,1), new Point(2,1)}
        };

        color = new Color(Color.PURPLE);
    }

    /**
     * Creates relative coordinates for an Z piece and sets color.
     */
    private void makeZ() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2)},
            //rotation 1
            {new Point(0,2), new Point(1,1), new Point(1,2), new Point(2,1)},
            //rotation 2
            {new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2)},
            //rotation 3
            {new Point(0,1), new Point(1,0), new Point(1,1), new Point(2,0)}
        };

        color = new Color(Color.RED);
    }
}

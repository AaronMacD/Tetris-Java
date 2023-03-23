package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Piece.
 */
public class Piece {
    private Point dimensions[][];

    //can be 0, 1, 2, or 3
    //TODO consider making this an enum
    private int rotationNum;

    //x-coordinate of the top-left corner of a piece
    private int row;
    private int col;

    private Color color;

    /**
     * Instantiates a new Piece.
     */
    Piece() {
        row = 1;
        col = 0;
        rotationNum = 0;
        generatePieceType();
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() { return this.row; }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() { return this.col; }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() { return this.color; }

    /**
     * Get dimensions point [ ] [ ].
     *
     * @return the point [ ] [ ]
     */
    public Point[][] getDimensions() {
        return dimensions;
    }

    /**
     * Gets rotation num.
     *
     * @return the rotation num
     */
    public int getRotationNum() { return this.rotationNum; }

    /**
     * Sets row.
     *
     * @param row the row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets col.
     *
     * @param col the col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets rotation num.
     *
     * @param rNum the r num
     */
    public void setRotationNum(int rNum) {
        this.rotationNum = rNum;
    }

    /**
     * Move left.
     */
    public void moveLeft(){ col--; }

    /**
     * Move right.
     */
    public void moveRight() { col++; }

    /**
     * Move down.
     */
    public void moveDown() {
        row++;
    }

    /**
     *
     * rNum  the current rotation number: 0, 1, 2, or 3
     * direction 1 if clockwise, -1 if counterclockwise
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

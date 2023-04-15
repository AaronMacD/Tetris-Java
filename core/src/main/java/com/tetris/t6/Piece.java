package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.Random;

/**
 * Piece Class.
 */
public final class Piece {
    /**
     * Dimensions for Piece(s).
     */
    private Point[][] dimensions;
    /**
     * Random Number Generator.
     */
    private Random rand = new Random();
    /**
     *  Rotation of Piece; can be 0, 1, 2, or 3 (Numbers are used to know how they are rotated).
     */
    private int rotationNum;
    /**
     * Row(s) of Piece.
     */
    //x-coordinate of the top-left corner of a piece
    private int row;
    /**
     * Col(s) of Piece.
     */
    private int col;
    /**
     * Color of Piece.
     */
    private Color color;

    /**
     * Constructor for Piece.
     */
    Piece() {
        row = 1;
        col = 0;
        rotationNum = 0;
        generatePieceType();
    }

    /**
     * @return the current row of the piece.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return the current col of the piece.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * @return the current color of piece.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the dimensions of piece.
     */
    public Point[][] getDimensions() {
        return dimensions.clone();
    }

    /**
     * @return the current rotation of the piece.
     */
    public int getRotationNum() {
        return this.rotationNum;
    }

    /**
     * @param aRow sets the row of the piece.
     */
    public void setRow(final int aRow) {
        this.row = aRow;
    }
    /**
     * @param aCol sets the col of the piece.
     */
    public void setCol(final int aCol) {
        this.col = aCol;
    }

    /**
     * @param rNum sets the rotationNum of the piece.
     */
    public void setRotationNum(final int rNum) {
        this.rotationNum = rNum;
    }

    /**
     * Moves the piece left.
     */
    public void moveLeft() {
        col--;
    }

    /**
     * Moves the piece right.
     */
    public void moveRight() {
        col++;
    }

    /**
     * Moves the piece down.
     */
    public void moveDown() {
        row++;
    }

    /**
     * rNum  the current rotation number: 0, 1, 2, or 3.
     * direction 1 if clockwise, -1 if counterclockwise
     */
    private void generatePieceType() {
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
     * Makes the J piece.
     */
    private void makeJ() {
        //Point values are row, col for individual square of a piece
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(1, 2)},
            //rotation 1
            {new Point(0, 1), new Point(0, 2), new Point(1, 1), new Point(2, 1)},
            //rotation 2
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            //rotation 3
            {new Point(0, 1), new Point(1, 1), new Point(2, 0), new Point(2, 1)}
        };

        color = new Color(Color.BLUE);

    }
    /**
     * Makes the L piece.
     */
    private void makeL() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 2), new Point(1, 0), new Point(1, 1), new Point(1, 2)},
            //rotation 1
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            //rotation 2
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)},
            //rotation 3
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)}
        };

        color = new Color(Color.ORANGE);
    }
    /**
     * Makes the Line piece.
     */
    // X = row, Y = col
    private void makeLine() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            //rotation 1
            {new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)},
            //rotation 2
            {new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)},
            //rotation 3
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)}
        };

        color = new Color(Color.CYAN);
    }

    /**
     * Makes the S piece.
     */
    private void makeS() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 1), new Point(0, 2), new Point(1, 0), new Point(1, 1)},
            //rotation 1
            {new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            //rotation 2
            {new Point(1, 1), new Point(1, 2), new Point(2, 0), new Point(2, 1)},
            //rotation 3
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)}
        };

        color = new Color(Color.GREEN);
    }

    /**
     * Makes the Square piece.
     */
    private void makeSquare() {
        //still represented as 2D array for convenience
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            //rotation 1
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            //rotation 2
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            //rotation 3
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        };

        color = new Color(Color.YELLOW);
    }

    /**
     * Makes the T piece.
     */
    private void makeT() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(1, 2)},
            //rotation 1
            {new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(2, 1)},
            //rotation 2
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 1)},
            //rotation 3
            {new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(2, 1)}
        };

        color = new Color(Color.PURPLE);
    }

    /**
     * Makes the Z piece.
     */
    private void makeZ() {
        dimensions = new Point[][] {
            //rotation 0
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            //rotation 1
            {new Point(0, 2), new Point(1, 1), new Point(1, 2), new Point(2, 1)},
            //rotation 2
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            //rotation 3
            {new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(2, 0)}
        };

        color = new Color(Color.RED);
    }
}

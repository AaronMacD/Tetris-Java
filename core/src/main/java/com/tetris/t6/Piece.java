package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.Random;


public final class Piece {
    private Point[][] dimensions;
    private Random rand = new Random();
    //(long) (Gdx.graphics.getDeltaTime()*10000)
    //can be 0, 1, 2, or 3
    //TODO consider making this an enum
    private int rotationNum;

    //x-coordinate of the top-left corner of a piece
    private int row;
    private int col;
    private Color color;

    Piece() {
        row = 1;
        col = 0;
        rotationNum = 0;
        generatePieceType();
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Color getColor() {
        return this.color;
    }

    public Point[][] getDimensions() {
        return dimensions;
    }
    public int getRotationNum() {
        return this.rotationNum;
    }

    public void setRow(final int aRow) {
        this.row = aRow;
    }
    public void setCol(final int aCol) {
        this.col = aCol;
    }
    public void setRotationNum(final int rNum) {
        this.rotationNum = rNum;
    }
    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }

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

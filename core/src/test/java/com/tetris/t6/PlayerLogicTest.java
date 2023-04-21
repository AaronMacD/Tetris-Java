package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PlayerLogicTest {



    @Test
    void moveDownOnce() {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertEquals(1, p.getCurrentPiece().getRow());
        p.setTimeMovement(2.5f);
        p.moveDownLogically();

        int row = p.getCurrentPiece().getRow();
        Assertions.assertEquals(2, row);
    }

    @Test
    void moveDownTwice() {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertEquals(1, p.getCurrentPiece().getRow());
        p.setTimeMovement(2.5f);
        p.moveDownLogically();
        p.setTimeMovement(2.5f);
        p.moveDownLogically();

        int row = p.getCurrentPiece().getRow();
        Assertions.assertEquals(3, row);
    }

    @Test
    void moveRightLeft() {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertEquals(0, p.getCurrentPiece().getCol());
        p.moveLeftRight(1);
        Assertions.assertEquals(1, p.getCurrentPiece().getCol());
        p.moveLeftRight(-1);
        Assertions.assertEquals(0, p.getCurrentPiece().getCol());
    }

    @Test
    void moveLeftAllTheWay() {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertEquals(0, p.getCurrentPiece().getCol());
        p.moveLeftRight(-1);
        p.moveLeftRight(-1);
        Assertions.assertEquals(0, p.getCurrentPiece().getCol());
    }

    @Test
    void moveRightAllTheWay() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("Square");
        p.setCurrentPiece(piece1);

        Assertions.assertEquals(0, p.getCurrentPiece().getCol());
        for (int i = 0; i < 11; i++) {
            p.moveLeftRight(1);
        }

        //8th column because the square piece is 2 cells wide
        Assertions.assertEquals(8, p.getCurrentPiece().getCol());
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 0, 2})
    void moveRightLeftException(int badVal) {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> p.moveLeftRight(badVal));
    }

    @Test
    void hardDropOnce() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("Square");
        p.setCurrentPiece(piece1);

        p.hardDrop();
        //20th row because square is 2 cells high
        Assertions.assertEquals(20, p.getCurrentPiece().getRow());
    }

    @Test
    void hardDropTwice() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("Square");
        p.setCurrentPiece(piece1);
        p.hardDrop();

        //lock squares in place
        p.setTimeMovement(2.5f);
        p.moveDownLogically();

        Piece piece2 = new Piece("Square");
        p.setCurrentPiece(piece2);
        Assertions.assertEquals(1, p.getCurrentPiece().getRow());
        p.hardDrop();

        //18th row because each square is 2 cells high
        Assertions.assertEquals(18, p.getCurrentPiece().getRow());
    }

    @Test
    void rotateClockwiseOnce() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("L");
        p.setCurrentPiece(piece1);

        p.rotate(1);
        Assertions.assertEquals(1, p.getCurrentPiece().getRotationNum());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1})
    void rotateFourTimes(int direction) {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("L");
        p.setCurrentPiece(piece1);
        //ensures ability to rotate CCW:
        p.getCurrentPiece().setCol(3);

        p.rotate(direction);
        p.rotate(direction);
        p.rotate(direction);
        p.rotate(direction);
        Assertions.assertEquals(0, p.getCurrentPiece().getRotationNum());
    }

    @Test
    void rotateCounterclockwiseOnce() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("L");
        p.setCurrentPiece(piece1);

        p.rotate(-1);
        Assertions.assertEquals(3, p.getCurrentPiece().getRotationNum());
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 0, 2})
    void rotateException(int badVal) {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> p.rotate(badVal));
    }

    @Test
    void rotateNotPossible() {
        PlayerLogic p = new PlayerLogic(1);
        Piece piece1 = new Piece("L");
        p.setCurrentPiece(piece1);
        //should not be able to rotate CW
        p.getCurrentPiece().setCol(8);

        Assertions.assertEquals(0, p.getCurrentPiece().getRotationNum());
        p.rotate(1);
        Assertions.assertEquals(0, p.getCurrentPiece().getRotationNum());
    }

    @Test
    void clearLine() {

    }



}

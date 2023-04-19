package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PieceTest {

    @Test
    void setRow() {
        Piece p = new Piece();
        p.setRow(10);

        Assertions.assertEquals(p.getRow(), 10);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 23})
    void setRowException(int badRow) {
        Piece p = new Piece();
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> p.setRow(badRow));
    }

    @Test
    void setCol() {
        Piece p = new Piece();
        p.setCol(10);

        Assertions.assertEquals(p.getCol(), 10);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    void setColException(int badCol) {
        Piece p = new Piece();
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> p.setCol(badCol));

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void setRotation(int rNum) {
        Piece p = new Piece();
        p.setRotationNum(rNum);
        Assertions.assertEquals(p.getRotationNum(), rNum);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 4})
    void setRotationException(int badRNum) {
        Piece p = new Piece();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> p.setRotationNum(badRNum));
    }


}

package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PlayerLogicTest {



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

    @ParameterizedTest
    @ValueSource(ints = {-2, 0, 2})
    void moveRightLeftException(int badVal) {
        PlayerLogic p = new PlayerLogic(1);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> p.moveLeftRight(badVal));
    }


}

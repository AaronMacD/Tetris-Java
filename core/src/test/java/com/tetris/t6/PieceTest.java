package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class PieceTest {

    @Test
    void constructorTest() {
        Piece p = new Piece();

        Assertions.assertEquals(p.getCol(), 0);
    }
}

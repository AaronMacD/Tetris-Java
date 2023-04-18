package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class HeldBlockTest {

    @Test
    void constructorBadOffset() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                HeldBlock h = new HeldBlock(15);
            });
    }

    @Test
    void setHeldValid() {
        HeldBlock h = new HeldBlock(0);
        Piece p = new Piece();
        h.setHeldPiece(p);

        Assertions.assertEquals(p, h.getHeldPiece());
    }

    @Test
    void setHeldNull() {
        HeldBlock h = new HeldBlock(0);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> h.setHeldPiece(null));
    }

    @Test
    void swapNull() {
        HeldBlock h = new HeldBlock(0);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> h.swapPiece(null));
    }

    @Test
    void swapValid() {
        HeldBlock h = new HeldBlock(0);
        Piece p1 = new Piece();
        Piece p2 = new Piece();
        Piece returnPiece;

        h.setHeldPiece(p1);
        returnPiece = h.swapPiece(p2);

        Assertions.assertEquals(returnPiece, p1);
        Assertions.assertEquals(h.getHeldPiece(), p2);
    }

}

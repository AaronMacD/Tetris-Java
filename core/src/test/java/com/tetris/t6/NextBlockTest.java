package com.tetris.t6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class NextBlockTest {

    @Test
    void constructorBadOffset() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                NextBlock n = new NextBlock(15);
            });
    }
}

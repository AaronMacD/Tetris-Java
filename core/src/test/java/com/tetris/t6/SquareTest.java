package com.tetris.t6;

import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SquareTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 23})
    void constructorBadRow(int badRow) {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                Square s = new Square(badRow, 0, Color.BLUE);
            });
    }
}

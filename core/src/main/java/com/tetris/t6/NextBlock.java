package com.tetris.t6;

import com.badlogic.gdx.utils.IntArray;

/**
 * The type Next block.
 */
public class NextBlock extends Piece {

    /**
     * The Game.
     */
    final TetrisGame game;
    /**
     * The Coordinates.
     */
    final IntArray coordinates = new IntArray();

    /**
     * Instantiates a new Next block.
     *
     * @param game the game
     */
//Constructor
    //TODO Figure out the parameters we're going to be passing to the constructor
    public NextBlock(TetrisGame game){
        this.game = game;
        coordinates.add(12,23);
    }

//    public BlockShape swapBlock(BlockShape newBlock){
//        BlockShape old = this.block;
//        this.block = newBlock;
//        displayPiece(this.block);
//        return old;
//    }

//    private void displayPiece(BlockShape bs) {
//        drawPiece(coordinates, this.block, Orientation.UP);
//    }
}

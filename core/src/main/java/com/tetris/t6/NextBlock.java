package com.tetris.t6;

import com.badlogic.gdx.utils.IntArray;

public class NextBlock extends Piece {

    final TetrisGame game;
    final IntArray coordinates = new IntArray();

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

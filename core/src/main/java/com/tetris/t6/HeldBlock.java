package com.tetris.t6;

import com.badlogic.gdx.utils.IntArray;
import sun.jvm.hotspot.opto.Block;

public class HeldBlock extends Piece {

    final GameController game;
    final IntArray coordinates = new IntArray();

    BlockShape block;

    //Constructor
    //TODO Figure out the parameters we're going to be passing to the constructor
    public HeldBlock(GameController game){
        this.game = game;
        block = BlockShape.EMPTY;
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

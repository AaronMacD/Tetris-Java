package com.tetris.t6;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class UserInterface implements Screen {

    final GameController game;

    GameBoard playArea;
    HeldBlock heldBlock;
    NextBlock nextBlock;
    SoundController SoundCtrl;


    Texture UIGraphic;



    public UserInterface(final GameController game){
        this.game = game;
        playArea = new GameBoard(game);
        heldBlock = new HeldBlock(game);
        //nextBlock = new NextBlock(game);


        game.camera.setToOrtho(false, 576, 832);
    }

    @Override
    public void show() {
    //Level Music can go here
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}

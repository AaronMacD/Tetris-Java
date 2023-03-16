package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameBoard implements Screen{

    GameController game;
    GameLogic gameplay;

    final Vector2 cell = new Vector2(32,32);


    //Graphics
    private Texture blueBlock;
    private Texture cyanBlock;
    private Texture greenBlock;
    private Texture greyBlock;
    private Texture orangeBlock;
    private Texture purpleBlock;
    private Texture redBlock;
    private Texture yellowBlock;

    private Texture image;
    //Sounds



    public GameBoard(GameController game) {
        this.game = game;
        gameplay = new GameLogic();



        //Loading Graphics
        blueBlock = new Texture(Gdx.files.internal("BlueBlock.png"));
        cyanBlock = new Texture(Gdx.files.internal("CyanBlock.png"));
        greenBlock = new Texture(Gdx.files.internal("GreenBlock.png"));
        greyBlock = new Texture(Gdx.files.internal("GreyBlock.png"));
        orangeBlock = new Texture(Gdx.files.internal("OrangeBlock.png"));
        purpleBlock = new Texture(Gdx.files.internal("PurpleBlock.png"));
        redBlock = new Texture(Gdx.files.internal("RedBlock.png"));
        yellowBlock = new Texture(Gdx.files.internal("YellowBlock.png"));

        //Loading Sounds

        //Loading Music

        image = blueBlock; //TODO replace this later
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        //Inputs


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

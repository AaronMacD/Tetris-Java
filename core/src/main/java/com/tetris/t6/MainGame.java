package com.tetris.t6;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    //Graphics
    private Texture blueBlock;
    private Texture cyanBlock;
    private Texture greenBlock;
    private Texture greyBlock;
    private Texture orangeBlock;
    private Texture purpleBlock;
    private Texture redBlock;
    private Texture yellowBlock;

    //Sounds


    //Camera and Renderer
    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void create() {
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

        //Setting up Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 576, 832);

        //Setting up SpriteBatch for drawing
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}

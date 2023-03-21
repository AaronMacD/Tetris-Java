package com.tetris.t6;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TetrisGame extends Game {

    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    GameScreen gameScreen;


    public void create() {
        batch = new SpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 400, 800);
        viewport = new FillViewport(400,800, camera);

        shapeRenderer = new ShapeRenderer();

        //TODO: uncomment
        //this.setScreen(new MainMenu(this));
        gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

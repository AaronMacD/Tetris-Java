package com.tetris.t6;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameController extends Game {

    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    GameLogic game;
    GameScreen gameScreen;


    public void create() {
        batch = new SpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 576 , 832);
        viewport = new FillViewport(576,832, camera);

        shapeRenderer = new ShapeRenderer();
        //TODO: uncomment
        //this.setScreen(new MainMenu(this));

        gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);

        game = new GameLogic(gameScreen);
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

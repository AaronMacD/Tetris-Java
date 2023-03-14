package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {

    final GameController game;

    public MainMenu(final GameController game) {
        this.game = game;
        game.camera.setToOrtho(false, 1200, 500);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Tetris T6!", 200, 250);
        game.font.draw(game.batch, "Click anywhere to start the game",  200, 150);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new UserInterface(game));
            dispose();
        }
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
    public void show() {
    //Main menu music can go here
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

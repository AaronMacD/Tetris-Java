package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class LossScreen implements Screen {

    final TetrisGame game;
    GameScreen gameScreen;
    GlyphLayout gl = new GlyphLayout();
    String menuText1 = "You've lost!!";
    String menuText2 = "Click anywhere to return to the main menu";

    public LossScreen(final TetrisGame game) {
        this.game = game;


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        gl.setText(game.font, menuText1);
        float w1 = gl.width;
        gl.setText(game.font, menuText2);
        float w2 = gl.width;


        game.batch.begin();
        game.font.draw(game.batch, menuText1, (Gdx.graphics.getWidth() - w1)/2, 750);
        game.font.draw(game.batch, menuText2,  (Gdx.graphics.getWidth() - w2)/2, 150);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new MenuScreen(this.game));
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
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

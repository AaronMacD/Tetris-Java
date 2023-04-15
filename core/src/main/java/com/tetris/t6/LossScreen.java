package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public final class LossScreen implements Screen {

    private final TetrisGame game;
    private GlyphLayout gl = new GlyphLayout();
    private String menuText1 = "Press escape to quit";
    private String menuText2 = "Or press any other key to return to the main menu";
    private Texture background;

    public LossScreen(final TetrisGame aGame) {
        this.game = aGame;
        background = new Texture(Gdx.files.internal("bg_loss.png"));
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        gl.setText(game.font, menuText1);
        float w1 = gl.width;
        gl.setText(game.font, menuText2);
        float w2 = gl.width;


        game.batch.begin();
        game.batch.draw(background, 0, 0);
        //game.font.draw(game.batch, menuText1,
        //    (Gdx.graphics.getWidth() - w1) / 2, 750);
        game.font.draw(game.batch, menuText1,
            (Gdx.graphics.getWidth() - w1) / 2, 150);
        game.font.draw(game.batch, menuText2,
            (Gdx.graphics.getWidth() - w2) / 2, 135);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new MenuScreen(this.game));
        }
    }

    @Override
    public void resize(final int width, final int height) {
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

package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The type Menu screen.
 */
public final class MenuScreen implements Screen {

    private final TetrisGame game;
    private GlyphLayout gl = new GlyphLayout();
    private String menuText2 = "Press 1 for Single Player or 2 for Versus!";
    private Texture logo;
    private Texture background;

    /**
     * Instantiates a new Menu screen.
     *
     * @param aGame the game
     */
    public MenuScreen(final TetrisGame aGame) {
        this.game = aGame;

        logo = new Texture(Gdx.files.internal("LOGO.png"));
        background = new Texture(Gdx.files.internal("bg_menu.png"));
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        float w1 = logo.getWidth();

        gl.setText(game.font, menuText2);
        float w2 = gl.width;


        game.batch.begin();
        game.batch.draw(background, 0, 0);

        game.batch.draw(logo, (Gdx.graphics.getWidth() -w1) / 2, 600);
        game.font.draw(game.batch, menuText2,
            (Gdx.graphics.getWidth() - w2) / 2, 440);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen(new GameScreen(game, 1));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            game.setScreen(new GameScreen(game, 2));
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

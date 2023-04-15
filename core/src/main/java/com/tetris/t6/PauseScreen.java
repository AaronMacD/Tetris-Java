package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public final class PauseScreen implements Screen {

    private final TetrisGame game;
    private GameScreen gameScreen;
    private GlyphLayout gl = new GlyphLayout();
    private String menuText1 = "Pause Menu";
    private String menuText2 = "Click anywhere to resume game";
    private String menuText3 = "or press escape to quit";
    private Texture background;

    public PauseScreen(final TetrisGame aGame, final GameScreen aGameScreen) {
        this.game = aGame;
        this.gameScreen = aGameScreen;
        background = new Texture(Gdx.files.internal("bg_pause.png"));


    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        gl.setText(game.font, menuText1);
        float w1 = gl.width;
        gl.setText(game.font, menuText2);
        float w2 = gl.width;
        gl.setText(game.font, menuText3);
        float w3 = gl.width;


        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.font.draw(game.batch, menuText1,
            (Gdx.graphics.getWidth() - w1) / 2, 750);
        game.font.draw(game.batch, menuText2,
            (Gdx.graphics.getWidth() - w2) / 2, 150);
        game.font.draw(game.batch, menuText3,
            (Gdx.graphics.getWidth() - w3) / 2, 135);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(this.gameScreen);
            gameScreen.resume();
            gameScreen.show();
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
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

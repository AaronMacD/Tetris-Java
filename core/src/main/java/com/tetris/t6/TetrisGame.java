package com.tetris.t6;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TetrisGame extends Game {

    SpriteBatch spriteBatch;
    PolygonSpriteBatch batch;
    TextureRegion region;
    Texture texture;
    BitmapFont font;
    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    GameScreen gameScreen;


    public void create() {
        spriteBatch = new SpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 400, 800);
        viewport = new FillViewport(400,800, camera);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        pixmap.dispose();
        texture = new Texture(pixmap); //remember to dispose of later

        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);

        shapeRenderer = new ShapeRenderer();
        ShapeDrawer drawer = new ShapeDrawer(batch, region);

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

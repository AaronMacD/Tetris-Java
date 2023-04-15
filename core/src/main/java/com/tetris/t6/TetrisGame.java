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

public final class TetrisGame extends Game {

    SpriteBatch spriteBatch;
    PolygonSpriteBatch batch;
    TextureRegion region;
    Texture texture;
    BitmapFont font;
    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    GameScreen gameScreen;
    ShapeDrawer drawer;
    Pixmap pixmap;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        batch = new PolygonSpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 900);
        viewport = new FillViewport(800, 900, camera);

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        texture = new Texture(pixmap); //remember to dispose of later
        region = new TextureRegion(texture, 0, 0, 1, 1);

        shapeRenderer = new ShapeRenderer();
        drawer = new ShapeDrawer(batch, region);

        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        pixmap.dispose();
    }
}

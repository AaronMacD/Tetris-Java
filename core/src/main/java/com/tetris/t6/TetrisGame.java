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

/**
 * The type Tetris game.
 */
public class TetrisGame extends Game {

    /**
     * The Sprite batch.
     */
    SpriteBatch spriteBatch;
    /**
     * The Batch.
     */
    PolygonSpriteBatch batch;
    /**
     * The Region.
     */
    TextureRegion region;
    /**
     * The Texture.
     */
    Texture texture;
    /**
     * The Font.
     */
    BitmapFont font;
    /**
     * The Camera.
     */
    OrthographicCamera camera;
    /**
     * The Viewport.
     */
    Viewport viewport;
    /**
     * The Shape renderer.
     */
    ShapeRenderer shapeRenderer;
    /**
     * The Game screen.
     */
    GameScreen gameScreen;
    /**
     * The Drawer.
     */
    ShapeDrawer drawer;
    /**
     * The Pixmap.
     */
    Pixmap pixmap;


    public void create() {
        spriteBatch = new SpriteBatch();
        batch = new PolygonSpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 400, 800);
        viewport = new FillViewport(400,800, camera);

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        texture = new Texture(pixmap); //remember to dispose of later
        region = new TextureRegion(texture, 0, 0, 1, 1);

        shapeRenderer = new ShapeRenderer();
        drawer = new ShapeDrawer(batch, region);

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
        pixmap.dispose();
    }
}

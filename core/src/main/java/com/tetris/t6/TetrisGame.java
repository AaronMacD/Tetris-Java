package com.tetris.t6;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

public final class TetrisGame extends Game { //NOPMD - suppressed AtLeastOneConstructor - TODO explain reason for suppression

    public PolygonSpriteBatch batch;
    private TextureRegion region;
    private Texture texture;
    public BitmapFont font;
    public OrthographicCamera camera;
    public Viewport viewport;
    public ShapeDrawer drawer;
    private Pixmap pixmap;


    @Override
    public void create() {
        batch = new PolygonSpriteBatch();
        // Use LibGDX's default Arial font.
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);

        texture = new Texture(pixmap); //remember to dispose of later
        region = new TextureRegion(texture, 0, 0, 1, 1);
        drawer = new ShapeDrawer(batch, region);

        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() { //NOPMD - suppressed UselessOverridingMethod - TODO explain reason for suppression
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        pixmap.dispose();
        texture.dispose();
    }
}

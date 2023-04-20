package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Holds sound effects and allows other classes to access them.
 */
public final class SoundManager {
    /**
     * Hold piece sound effect.
     */
    private Sound hold;
    /**
     * Lock sound effect.
     */
    private final Sound lock;
    /**
     * Rotate sound effect.
     */
    private final Sound rotate;
    /**
     * Line clear sound effect.
     */
    private final Sound lineClear;
    /**
     * Tetris (four lines cleared) sound effect.
     */
    private final Sound tetris;

    /**
     * Initializes the sound effects.
     */
    public SoundManager() {
        hold = Gdx.audio.newSound(Gdx.files.internal("hold.wav"));
        lock = Gdx.audio.newSound(Gdx.files.internal("lock.wav"));
        rotate = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        lineClear = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetris = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));
    }

    /**
     * Plays the hold sound effect.
     */
    public void playHold() {
        hold.play(1.0f);
    }

    /**
     * Plays the lock sound effect.
     */
    public void playLock() {
        lock.play(1.0f);
    }

    /**
     * Plays the rotate sound effect.
     */
    public void playRotate() {
        rotate.play(1.0f);
    }

    /**
     * Plays the lineClear sound effect.
     */
    public void playLineClear() {
        lineClear.play(1.0f);
    }

    /**
     * Plays the tetris sound effect.
     */
    public void playTetris() {
        tetris.play(1.0f);
    }

}

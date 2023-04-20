package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
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

    public SoundManager() {
        hold = Gdx.audio.newSound(Gdx.files.internal("hold.wav"));
        lock = Gdx.audio.newSound(Gdx.files.internal("lock.wav"));
        rotate = Gdx.audio.newSound(Gdx.files.internal("rotate.wav"));
        lineClear = Gdx.audio.newSound(Gdx.files.internal("line_clear.wav"));
        tetris = Gdx.audio.newSound(Gdx.files.internal("tetris.wav"));
    }

    public void playHold() {
        hold.play(1.0f);
    }

    public void playLock() {
        lock.play(1.0f);
    }

    public void playRotate() {
        rotate.play(1.0f);
    }

    public void playLineClear() {
        lineClear.play(1.0f);
    }

    public void playTetris() {
        tetris.play(1.0f);
    }

}

package com.tetris.t6;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public final class SoundController extends ApplicationAdapter {
    private Sound sound;
    private String soundName;

    public SoundController(String s) {
        super();
        this.soundName = s;
    }

    public void create() {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(soundName));
        this.sound.play(0.5f);

    }

    @Override
    public void dispose() {
        this.sound.dispose();
    }


}

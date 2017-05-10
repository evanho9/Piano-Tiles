package com.evanho9.pianotiles.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by evanh on 5/9/2017.
 */

public enum Chord {
    C_MAJOR();
    

    public Sound sound;

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Sound getSound() {
        return sound;
    }
}

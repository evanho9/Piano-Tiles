package com.evanho9.pianotiles.gameobject;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evanho9.pianotiles.game.GameLogic;
import com.evanho9.pianotiles.game.PianoTiles;

/**
 * Created by evanh on 5/3/2017.
 */

public class Tile extends Actor {

    public final static float TILE_WIDTH = PianoTiles.WORLD_WIDTH/ GameLogic.NUMBER_OF_COLUMNS;

    private TextureRegion texture;
    private Color color;
    private Sound sound;
    private int column;

    public Tile(int column, float yPos) {
        setPosition(column * TILE_WIDTH, yPos);
        this.column = column;
        this.texture = null;
    }

    public int getColumn() {
        return column;
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.setColor(color);
            batch.begin();
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
            batch.end();
        }
    }

    public void playSound() {
        sound.play();
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
        setSize(TILE_WIDTH, texture.getRegionHeight());
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

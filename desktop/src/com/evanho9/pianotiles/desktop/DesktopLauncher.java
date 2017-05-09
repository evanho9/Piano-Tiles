package com.evanho9.pianotiles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.evanho9.pianotiles.game.PianoTiles;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Piano Tiles";
        config.width = PianoTiles.WORLD_WIDTH;
        config.height = PianoTiles.WORLD_HEIGHT;
		new LwjglApplication(new PianoTiles(), config);
	}
}

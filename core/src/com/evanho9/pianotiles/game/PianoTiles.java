package com.evanho9.pianotiles.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.evanho9.pianotiles.screen.GameScreen;
import com.evanho9.pianotiles.screen.MainMenuScreen;


public class PianoTiles extends Game {
	//public final static String MUSIC_PATH = "music.wav";
	public final static String MASTER_PATH = "master.pack";
	public final static String FONT_PATH = "timeburnernormal.ttf";
	public final static String C_PATH = "c.wav";
	public final static String E_PATH = "e.wav";
	public final static String G_PATH = "g.wav";
	public final static String HIGHC_PATH = "highc.wav";
    public final static int WORLD_WIDTH = 630;
    public final static int WORLD_HEIGHT = 1120;

	private AssetManager assetManager;
    private GameLogic gameLogic;

	@Override
	public void create () {
		loadAssets();
		setScreen(new MainMenuScreen(this));
	}

	public void loadAssets() {
        InternalFileHandleResolver fileHandler = new InternalFileHandleResolver();
        assetManager = new AssetManager(fileHandler);
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(fileHandler));

		assetManager.load(MASTER_PATH, TextureAtlas.class);
        assetManager.load(FONT_PATH, FreeTypeFontGenerator.class);
		assetManager.load(C_PATH, Sound.class);
		assetManager.load(E_PATH, Sound.class);
		assetManager.load(G_PATH, Sound.class);
		assetManager.load(HIGHC_PATH, Sound.class);
		assetManager.finishLoading();
	}

	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
        return assetManager;
	}

	public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

}

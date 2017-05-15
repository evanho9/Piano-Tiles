package com.evanho9.pianotiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evanho9.pianotiles.game.GameLogic;
import com.evanho9.pianotiles.game.PianoTiles;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by evanh on 5/2/2017.
 */

public class GameScreen implements Screen {
    private PianoTiles pianoTiles;
    private GameLogic gameLogic;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;
    private BitmapFont font;

    private TextureAtlas textureAtlas;
    private Music gameMusic;
    private TextureRegion background;
    private Stage hud;

    private ImageButton deathButton;


    public GameScreen(PianoTiles pianoTiles) {
        this.pianoTiles = pianoTiles;
        gameLogic = new GameLogic(pianoTiles);
        pianoTiles.setGameLogic(gameLogic);
        initUtils();
        initAssets();
        initHUD();
    }

    public void initUtils() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT, camera);
        viewport.apply(true);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

    }

    public void initAssets() {
        //gameMusic = pianoTiles.getAssetManager().get(PianoTiles.MUSIC_PATH);
        textureAtlas = pianoTiles.getAssetManager().get(PianoTiles.MASTER_PATH);
        background = textureAtlas.findRegion("background");
    }

    public void initHUD() {
        hud = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        hud.getRoot().getColor().a = 0;
        hud.getRoot().addAction(fadeIn(1.5f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        delta = Math.min(delta, 0.03f);

        Gdx.input.setInputProcessor(hud);
        hud.draw();
        hud.act();

        batch.begin();
        //batch.draw(background, 0, 0, PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT);
        batch.end();

        gameLogic.update();
        gameLogic.render(batch);

        if (gameLogic.getTiles().size() > 0 && gameLogic.getTiles().get(0).getY()+gameLogic.getTiles().get(0).getHeight() <= 0) {
            pianoTiles.setScreen(new DeathScreen(pianoTiles));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

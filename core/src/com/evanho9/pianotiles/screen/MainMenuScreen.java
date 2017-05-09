package com.evanho9.pianotiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evanho9.pianotiles.game.PianoTiles;
import com.evanho9.pianotiles.util.ShapeColor;

/**
 * Created by evanh on 5/4/2017.
 */

public class MainMenuScreen implements Screen {

    private PianoTiles pianoTiles;

    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private TextureRegion background;

    public MainMenuScreen(PianoTiles pianoTiles) {
        this.pianoTiles = pianoTiles;
        initUtils();
        initHUD();
        createTitle();
    }

    public void initUtils() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT, camera);
        viewport.apply(true);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    private BitmapFont generateFont(int size, boolean bold) {
        AssetManager assetManager = pianoTiles.getAssetManager();
        FreeTypeFontGenerator fontGenerator = bold ? assetManager.get(pianoTiles.FONT_PATH, FreeTypeFontGenerator.class)
                : assetManager.get(pianoTiles.FONT_PATH, FreeTypeFontGenerator.class);

        FreeTypeFontGenerator.FreeTypeFontParameter fontSettings = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontSettings.size = size;
        fontSettings.minFilter = Texture.TextureFilter.Linear;
        fontSettings.magFilter = Texture.TextureFilter.Linear;

        return fontGenerator.generateFont(fontSettings);
    }

    private void createTitle() {
        BitmapFont titleFont = generateFont(100, true);
        titleFont.getData().markupEnabled = true;
        Label title = new Label("Piano Tiles", new Label.LabelStyle(titleFont, ShapeColor.BLUE.color));
        title.setPosition(pianoTiles.WORLD_WIDTH/2 - title.getWidth()/2, 4*pianoTiles.WORLD_HEIGHT/5.5f);

        stage.addActor(title);
    }

    public void initHUD() {
        stage = new Stage(viewport, batch);

        AssetManager assetManager = pianoTiles.getAssetManager();

        TextureRegion playButtonTextureRegion = assetManager.get(PianoTiles.MASTER_PATH, TextureAtlas.class).findRegion("playbutton");
        Drawable playButtonDrawable = new TextureRegionDrawable(playButtonTextureRegion);
        ImageButton playButton = new ImageButton(playButtonDrawable);
        playButton.setSize(PianoTiles.WORLD_WIDTH/3, PianoTiles.WORLD_HEIGHT/3);
        playButton.setPosition(PianoTiles.WORLD_WIDTH/2 - playButton.getWidth()/2, PianoTiles.WORLD_HEIGHT/2 - playButton.getHeight()/2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pianoTiles.setScreen(new GameScreen(pianoTiles));
                //mainMenuMusic.stop();
            }
        });
        TextureRegion creditsButtonTextureRegion = assetManager.get(PianoTiles.MASTER_PATH, TextureAtlas.class).findRegion("creditsbutton");
        Drawable creditsButtonDrawable = new TextureRegionDrawable(creditsButtonTextureRegion);
        ImageButton creditsButton = new ImageButton(creditsButtonDrawable);
        creditsButton.setSize(PianoTiles.WORLD_WIDTH/4, PianoTiles.WORLD_HEIGHT/4);
        creditsButton.setPosition(0,0);
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pianoTiles.setScreen(new GameScreen(pianoTiles));
                //mainMenuMusic.stop();
            }
        });

        stage.addActor(playButton);
        stage.addActor(creditsButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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

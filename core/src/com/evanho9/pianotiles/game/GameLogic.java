package com.evanho9.pianotiles.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evanho9.pianotiles.gameobject.Tile;
import com.evanho9.pianotiles.screen.DeathScreen;
import com.evanho9.pianotiles.util.Chord;
import com.evanho9.pianotiles.util.ShapeColor;

import java.util.ArrayList;

/**
 * Created by evanh on 5/3/2017.
 */

public class GameLogic {

    public static final int NUMBER_OF_COLUMNS = 4;
    public static final float INCREASE_FACTOR = 1.01f;

    //game utils
    private PianoTiles pianoTiles;
    private AssetManager assetManager;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Sound currentChord;
    private int chordProgression;

    //game stat keepers
    private int tilesSpawned;
    private float score;
    private int tilesMatched;

    //game variables
    private float delta;
    private float velocity = 10;
    public float delay = 40;
    private ArrayList<Tile> tiles;

    public GameLogic(PianoTiles pianoTiles) {
        this.assetManager = pianoTiles.getAssetManager();
        this.atlas = assetManager.get(PianoTiles.MASTER_PATH, TextureAtlas.class);

        batch = new SpriteBatch();
        this.pianoTiles = pianoTiles;
        tiles = new ArrayList<Tile>();
        tilesSpawned = 0;
        score = 0;
        tilesMatched = 0;
        delta = 0;
        initHUD();
        initFont();
    }

    public void initHUD() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT, camera);
        viewport.apply(true);
        camera.update();
        stage = new Stage(viewport, batch);

        Skin deathButtonSkin= new Skin();
        deathButtonSkin.addRegions(pianoTiles.getAssetManager().get(PianoTiles.MASTER_PATH, TextureAtlas.class));
        ImageButton deathButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("raw/deathbutton.png")))));
        deathButton.setSize(PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT);
        deathButton.setPosition(0, 0);
        deathButton.setVisible(true);
        deathButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoTiles.setScreen(new DeathScreen(pianoTiles));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        stage.addActor(deathButton);
    }

    public void addTile() {
        tilesSpawned++;
        final Tile tile = generateTile();
        tiles.add(tile);
        tile.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (tiles.indexOf(tile) == 0) {
                    score += 100;
                    tilesMatched++;
                    /**if (tilesMatched % 4 == 0) {
                        progressChord();
                        currentChord.play();
                    }
                    else {
                        tile.playSound();
                    }**/
                    tile.playSound();
                    tiles.remove(0);
                }
                else {
                    pianoTiles.setScreen(new DeathScreen(pianoTiles));
                }

                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        stage.addActor(tile);
    }

    public Tile generateTile() {
        Tile tile = new Tile((int)(Math.random()*NUMBER_OF_COLUMNS), PianoTiles.WORLD_HEIGHT);
        int col = tile.getColumn();
        switch (col) {
            case 0:
                tile.setColor(ShapeColor.RED.color);
                tile.setSound(assetManager.get(PianoTiles.C_PATH, Sound.class));
                break;
            case 1:
                tile.setColor(ShapeColor.GREEN.color);
                tile.setSound(assetManager.get(PianoTiles.E_PATH, Sound.class));
                break;
            case 2:
                tile.setColor(ShapeColor.BLUE.color);
                tile.setSound(assetManager.get(PianoTiles.G_PATH, Sound.class));
                break;
            case 3:
                tile.setColor(ShapeColor.YELLOW.color);
                tile.setSound(assetManager.get(PianoTiles.HIGHC_PATH, Sound.class));
                break;
        }
        tile.setTexture(atlas.findRegion("tile"));
        return tile;
    }

    public void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("timeburnernormal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
    }

    public void progressChord() {
        if (chordProgression <= 3) {
            chordProgression++;
            currentChord = Chord.values()[chordProgression].getSound();
        }
        else {
            chordProgression = 0;
            currentChord = Chord.values()[chordProgression].getSound();
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public BitmapFont getFont() {
        return font;
    }

    public float getScore() {
        return score;
    }

    public int getTilesMatched() {
        return tilesMatched;
    }

    public int getTilesSpawned() {
        return tilesSpawned;
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
        delta++;
        if (delta >= delay) {
            delta = 0;
            addTile();
            if (velocity <= 40) {
                velocity *= INCREASE_FACTOR;
                if (delay >= 17) {
                    delay -= INCREASE_FACTOR;
                }
            }
        }
        for (Tile tile : tiles) {
            tile.setY(tile.getY()- velocity);
        }
    }

    public void render(SpriteBatch batch) {
        stage.draw();
        for (Tile tile : tiles) {
            tile.render(batch);
        }
        batch.begin();
        font.draw(batch, "Score: " + score, PianoTiles.WORLD_WIDTH/12, 9*PianoTiles.WORLD_HEIGHT/10 );
        batch.end();
    }
}

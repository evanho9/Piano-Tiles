package com.evanho9.pianotiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.evanho9.pianotiles.game.GameLogic;
import com.evanho9.pianotiles.game.PianoTiles;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by evanh on 5/3/2017.
 */

public class DeathScreen implements Screen {

    private PianoTiles pianoTiles;
    private GameLogic gameLogic;

    private Stage hud;
    private ImageButton replayButton;
    private TextureRegion background;

    public DeathScreen(PianoTiles pianoTiles, GameLogic gameLogic) {
        this.pianoTiles = pianoTiles;
        this.gameLogic = gameLogic;
        background = pianoTiles.getAssetManager().get(PianoTiles.MASTER_PATH, TextureAtlas.class).findRegion("background");
        initHUD();
    }

    public void initHUD() {
        hud = new Stage(new StretchViewport(PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT));

        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(pianoTiles.getAssetManager().get(PianoTiles.MASTER_PATH, TextureAtlas.class));

        replayButton = new ImageButton(buttonSkin.getDrawable("playbutton"));
        replayButton.setSize(PianoTiles.WORLD_WIDTH/3, PianoTiles.WORLD_HEIGHT/3);
        replayButton.setPosition(PianoTiles.WORLD_WIDTH/2 - replayButton.getWidth()/2, PianoTiles.WORLD_HEIGHT/2 - replayButton.getHeight()/2);
        replayButton.setVisible(true);
        replayButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchScreen(pianoTiles, new GameScreen(pianoTiles));
            }
        });
        hud.addActor(replayButton);
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
        Gdx.input.setInputProcessor(hud);
        hud.act();

        hud.getBatch().begin();
        //hud.getBatch().draw(background, 0 ,0, PianoTiles.WORLD_WIDTH, PianoTiles.WORLD_HEIGHT);
        gameLogic.getFont().draw(hud.getBatch(), "Score: " + gameLogic.getScore(), PianoTiles.WORLD_WIDTH/12, 9*PianoTiles.WORLD_HEIGHT/10);
        gameLogic.getFont().draw(hud.getBatch(), "Tiles Matched: " + gameLogic.getTilesMatched(), PianoTiles.WORLD_WIDTH/12, 8*PianoTiles.WORLD_HEIGHT/10);
        gameLogic.getFont().draw(hud.getBatch(), "Tiles Spawned: " + gameLogic.getTilesSpawned(), PianoTiles.WORLD_WIDTH/12, 7*PianoTiles.WORLD_HEIGHT/10);
        hud.getBatch().end();
        hud.draw();

    }
    public void switchScreen(final PianoTiles pianoTiles, final Screen newScreen){
        hud.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(fadeOut(0.5f));
        sequenceAction.addAction(run(new Runnable() {
            @Override
            public void run() {
                pianoTiles.setScreen(newScreen);
            }
        }));
        hud.getRoot().addAction(sequenceAction);
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
        hud.getRoot().getColor().a = 0;
        hud.getRoot().addAction(fadeIn(2.0f));
    }

    @Override
    public void dispose() {

    }
}

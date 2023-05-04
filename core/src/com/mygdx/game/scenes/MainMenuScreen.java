package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SlidePuzzle;

public class MainMenuScreen implements Screen {

    private final Stage stage;

    public MainMenuScreen(final SlidePuzzle game) {
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin();
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_button.png"))));
        skin.add("default", buttonStyle);

        ImageButton playButton = new ImageButton(skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showGameScreen();
            }
        });

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        Label playLabel = new Label("Play", labelStyle);
        playLabel.setAlignment(Align.center);

        table.add(playButton).center().padBottom(20f).row();

        Button authorButton = new TextButton("Authors", initializeButtonStyle());
        authorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.showAuthorScreen();
            }
        });

        table.add(authorButton).center();

        float duration = 0.5f;
        float scale = 1.1f;

        playButton.setTransform(true);
        playButton.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(scale, scale, duration),
                Actions.scaleTo(1f, 1f, duration)
        )));

        playLabel.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(scale, scale, duration),
                Actions.scaleTo(1f, 1f, duration)
        )));

        Gdx.input.setInputProcessor(stage);
    }




    private Skin initializeButtonStyle(){
      Skin skin = new Skin();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = Color.BLACK;
        skin.add("default", buttonStyle);
        return skin;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#ffd300"));
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}

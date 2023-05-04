package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FontGenerator;
import com.mygdx.game.SlidePuzzle;

public class MainMenuScreen implements Screen {

    private final Stage stage;

    public MainMenuScreen(final SlidePuzzle game) {
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Button playButton = new TextButton("Play",initializeButtonStyle());
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showGameScreen();
            }
        });

        table.add(playButton).center().padBottom(20f).row();

        Button authorButton = new TextButton("Authors", initializeButtonStyle());
        authorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.showAuthorScreen();
            }
        });

        table.add(authorButton).center();

//        float duration = 0.7f;
//        float scale = 1.1f;
//
//        playButton.setTransform(true);
//        playButton.setOrigin(Align.center);
//        playButton.addAction(Actions.forever(Actions.sequence(
//                Actions.scaleTo(scale, scale, duration,Interpolation.sine),
//                Actions.scaleTo(1f, 1f, duration,Interpolation.sine)
//        )));
//        authorButton.setTransform(true);
//        authorButton.setOrigin(Align.center);
//        authorButton.addAction(Actions.forever(Actions.sequence(
//                Actions.scaleTo(1f, 1f, duration, Interpolation.circle),
//                Actions.scaleTo(0.8f, 0.8f, duration,Interpolation.circle)
//        )));

        Gdx.input.setInputProcessor(stage);
    }




    private Skin initializeButtonStyle(){
      Skin skin = new Skin();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = FontGenerator.generateFont(28,"roboto_bold.ttf");
        buttonStyle.fontColor = buttonStyle.font.getColor();
        skin.add("default", buttonStyle);
        return skin;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#d9d375"));
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

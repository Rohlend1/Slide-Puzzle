package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.util.FontGenerator;
import com.mygdx.game.SlidePuzzle;

public class MainMenuScreen implements Screen {

    private final Stage stage;
    private final Music music;

    public MainMenuScreen(final SlidePuzzle game) {
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Button playButton = new TextButton("Play",initializeButtonStyle());
        music = Gdx.audio.newMusic(Gdx.files.internal("main_theme.mp3"));

        music.setLooping(true);
        music.setVolume(0.3f);

        music.play();
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
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
        music.dispose();
    }
}

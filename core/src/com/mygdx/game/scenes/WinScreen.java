package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.util.FontGenerator;
import com.mygdx.game.SlidePuzzle;

public class WinScreen implements Screen {
    private final SlidePuzzle game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;

    public WinScreen(SlidePuzzle game) {
        this.game = game;

    }

    @Override
    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        font = new BitmapFont();

        backgroundTexture = new Texture("sprites/background.png");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontGenerator.generateFont(24,"fonts/roboto_bold.ttf");
        labelStyle.fontColor = labelStyle.font.getColor();
        Label messageLabel = new Label("Congratulations! You win!", labelStyle);

        messageLabel.setPosition( Gdx.graphics.getWidth() / 2 - messageLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = FontGenerator.generateFont(18,"fonts/roboto_medium.ttf");
        buttonStyle.fontColor = buttonStyle.font.getColor();
        Button nextLevelButton = new TextButton("Next Level", buttonStyle);
        nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2 - nextLevelButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 100);

        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.nextLevel();
            }
        });

        stage.addActor(messageLabel);
        stage.addActor(nextLevelButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
        stage.dispose();
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}


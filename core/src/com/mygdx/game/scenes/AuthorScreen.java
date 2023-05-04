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

public class AuthorScreen implements Screen {

    private final Stage stage;

    public AuthorScreen(final SlidePuzzle game) {
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyleButton = new Label.LabelStyle();
        Label.LabelStyle labelStyleText = new Label.LabelStyle();
        Label.LabelStyle labelStyleTitle = new Label.LabelStyle();

        Skin skin = new Skin();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font =  FontGenerator.generateFont(32,"roboto_bold.ttf");
        buttonStyle.fontColor = buttonStyle.font.getColor();
        skin.add("default",skin);

        labelStyleButton.font = FontGenerator.generateFont(28,"roboto_bold.ttf");
        labelStyleTitle.font = FontGenerator.generateFont(26,"roboto_bold.ttf");
        labelStyleText.font = FontGenerator.generateFont(24,"roboto.ttf");

        labelStyleText.fontColor = labelStyleText.font.getColor();
        labelStyleButton.fontColor = labelStyleText.font.getColor();
        labelStyleTitle.fontColor = labelStyleText.font.getColor();

        Label titleLabel = new Label("Authors:", labelStyleButton);

        Label author1Label = new Label("Rohlend", labelStyleText);
        Label author2Label = new Label("Barbaratimson", labelStyleText);

        Label musicLabel = new Label("Music: ",labelStyleTitle);
        Label codeLabel = new Label("Code: ",labelStyleTitle);
        Label designLabel = new Label("Design: ",labelStyleTitle);


        table.add(titleLabel).center().padBottom(20f).row();

        table.add(codeLabel).center().padBottom(20f).row();

        table.add(new Label(author1Label.getText(), labelStyleText)).center().padBottom(10f).row();
        table.add(new Label(author2Label.getText(), labelStyleText)).center().padBottom(20f).row();

        table.add(musicLabel).center().padBottom(20f).row();
        table.add(new Label(author2Label.getText(), labelStyleText)).center().padBottom(20f).row();

        table.add(designLabel).center().padBottom(20f).row();
        table.add(new Label(author1Label.getText(), labelStyleText)).center().padBottom(20f).row();

        Button backButton = new TextButton("Back",buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showMainMenuScreen();
            }
        });
        table.add(backButton).center().padTop(20f);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
//        buttonImage.addAction(Actions.forever(Actions.sequence(
//                Actions.scaleTo(1.1f, 1.1f, 0.5f),
//                Actions.scaleTo(1f, 1f, 0.5f)
//        )));
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

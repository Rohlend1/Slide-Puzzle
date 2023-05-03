package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class AuthorScreen implements Screen {

    private final SlidePuzzle game;
    private final Stage stage;
//    private final Animation<TextureAtlas.AtlasRegion> buttonAnimation;
    private final Image buttonImage;

    public AuthorScreen(final SlidePuzzle game) {
        this.game = game;
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        Label titleLabel = new Label("Authors", labelStyle);
        Label author1Label = new Label("Rohlend", labelStyle);
        Label author2Label = new Label("Barbaratimson", labelStyle);

        table.add(titleLabel).center().padBottom(20f).row();
        table.add(author1Label).center().padBottom(10f).row();
        table.add(author2Label).center().padBottom(10f).row();
//        buttonAnimation = createButtonAnimation();
//        buttonImage = new Image(buttonAnimation.getKeyFrame(0));
        buttonImage = new Image(new Texture(Gdx.files.internal("back_button.png")));
        buttonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showMainMenuScreen();
            }
        });
        table.add(buttonImage).center().padTop(20f);

        Gdx.input.setInputProcessor(stage);
    }

    private Animation<TextureAtlas.AtlasRegion> createButtonAnimation() {
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("but_down.png"));
        Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(0.1f, buttonAtlas.getRegions());
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    @Override
    public void show() {
        buttonImage.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.5f),
                Actions.scaleTo(1f, 1f, 0.5f)
        )));
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

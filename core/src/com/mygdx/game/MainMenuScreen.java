package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {

    private final Stage stage;

    public MainMenuScreen(final SlidePuzzle game) {
        stage = new Stage();

        // Создание таблицы для размещения элементов интерфейса
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Создание кнопки "Play"
        Button playButton = new TextButton("", game.getSkin());
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Переключение на экран игры (GameScreen)
                game.showGameScreen();
            }
        });

        // Добавление кнопки на таблицу
        table.add(playButton).pad(10);

        // Установка входного процессора для обработки ввода с клавиатуры и мыши
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Отрисовка сцены
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

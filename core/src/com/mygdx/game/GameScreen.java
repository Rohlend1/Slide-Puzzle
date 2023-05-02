package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;



public class GameScreen implements Screen {

    private final SpriteBatch batch;
    private final Array<Cell> cells;
    private final KeyboardAdapter inputProcessor;
    private final int currentLevel;
    public static Cell onClickecCell;

    public GameScreen(int currentLevel, KeyboardAdapter inputProcessor) {
        this.currentLevel = currentLevel;
        batch = new SpriteBatch();
        cells = new Array<>();
        this.inputProcessor = inputProcessor;

        // Создание ячеек для игры
        createCells();
    }

    private void createCells() {
        for (int i = 0; i < Math.pow(currentLevel + 2, 2); i++) {
            cells.add(new Cell(200 + i / (currentLevel + 2) * 100, 200 + i % (currentLevel + 2) * 100, 100, 100));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vector2 mousePos = inputProcessor.getMousePos();
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();

        for (Cell cell : cells) {
            if (onClickecCell == null) {
                onClickecCell = cell.check(mousePos);
            }
            if (onClickecCell != null && onClickecCell.getId() == cell.getId()) {
                cell.rotateTo(mousePos);
            }
            Cell intersectedCell = cell.isIntersect(Arrays.asList(cells.toArray()));
            if (!inputProcessor.isDown() && intersectedCell == null) cell.toDefaultPostion();
            else if (!inputProcessor.isDown() && intersectedCell != null) {
                cell.exchangeCells(intersectedCell, currentLevel + 2);
                cell.toDefaultPostion();
                intersectedCell.toDefaultPostion();
                intersectedCell.render(batch);
            }
            if (onClickecCell == null || onClickecCell.getId() != cell.getId()) cell.render(batch);
        }
        if (onClickecCell != null) onClickecCell.render(batch);
        batch.end();
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
        // Освобождение ресурсов
        for (Cell cell : cells) {
            cell.dispose();
        }
        batch.dispose();
    }
}

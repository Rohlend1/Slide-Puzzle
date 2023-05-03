package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;



public class GameScreen implements Screen {

    private final SpriteBatch batch;
    private final Array<Cell> cells;
    private final KeyboardAdapter inputProcessor;
    private final int numberOfCells;
    public static Cell onClickecCell;
    private int startPositionX;
    private int startPositionY;

    public GameScreen(int numberOfCells, KeyboardAdapter inputProcessor) {
        this.numberOfCells = numberOfCells;
        batch = new SpriteBatch();
        cells = new Array<>();
        this.inputProcessor = inputProcessor;
        countStartPosition();
        createCells();
    }

    private void countStartPosition(){
        startPositionX = Gdx.graphics.getWidth()/2-(Cell.CELL_WIDTH*numberOfCells)/2;
        startPositionY = Gdx.graphics.getHeight()/2-(Cell.CELL_WIDTH*numberOfCells)/2;
    }

    private void createCells() {
        for (int i = 0; i < numberOfCells*numberOfCells; i++) {
            cells.add(new Cell( startPositionX + i / (numberOfCells) * Cell.CELL_WIDTH,
                    startPositionY + i % (numberOfCells) * Cell.CELL_HEIGHT,
                    Cell.CELL_WIDTH, Cell.CELL_HEIGHT));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vector2 mousePos = inputProcessor.getMousePos();
        ScreenUtils.clear(Color.valueOf("#ffd300"));
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
                cell.exchangeCells(intersectedCell,  numberOfCells);
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
        for (Cell cell : cells) {
            cell.dispose();
        }
        batch.dispose();
    }
}

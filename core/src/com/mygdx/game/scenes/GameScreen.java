package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Cell;
import com.mygdx.game.KeyboardAdapter;
import com.mygdx.game.SlidePuzzle;

import java.util.ArrayList;
import java.util.Arrays;


public class GameScreen implements Screen {

    private final SpriteBatch batch;
    private final Array<Cell> cells;
    private final KeyboardAdapter inputProcessor;
    private final int numberOfCells;
    public static Cell onClickecCell;
    private int startPositionX;
    private int startPositionY;
    private Texture branchTexture;
    private Vector2 branchPosition;
    private Color branchColor;
    private Array<Cell> validationCellGrid = new Array<>();
    private boolean isWin = false;
    private final SlidePuzzle game;
    private final Texture ipaitLogoTexture =new Texture(Gdx.files.internal("sprites/ipait_logo.png"));
    private Vector2 ipaitLogoPosition;
    private static final int LOGO_WIDTH = 200;
    private static final int LOGO_HEIGHT = 212;

    private static Texture levelCompressedPicture;
    private static Vector2 compressedLevelPosition;


    public GameScreen(int numberOfCells, KeyboardAdapter inputProcessor,SlidePuzzle game) {
        this.numberOfCells = numberOfCells;

        Music music = Gdx.audio.newMusic(Gdx.files.internal("music/gameplay_theme.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        batch = new SpriteBatch();
        cells = new Array<>();
        this.game = game;
        this.inputProcessor = inputProcessor;

        createValidationGrid();
        countStartPosition();
        createCells();


        while(!countInversions()) {
            cells.clear();
            createCells();
        }

    }

    private void countStartPosition(){
        startPositionX = Gdx.graphics.getWidth()/2-(Cell.CELL_WIDTH*numberOfCells)/2;
        startPositionY = Gdx.graphics.getHeight()/2-(Cell.CELL_WIDTH*numberOfCells)/2;
    }

    private void createValidationGrid(){
        validationCellGrid = new Array<>();
        for(int i = 0; i < numberOfCells*numberOfCells;i++){
            Cell cell = new Cell(i,startPositionX + i / (numberOfCells) * Cell.CELL_WIDTH,
                    startPositionY + i % (numberOfCells) * Cell.CELL_HEIGHT,
                    Cell.CELL_WIDTH, Cell.CELL_HEIGHT,"images/image"+i+".jpg");
            validationCellGrid.add(cell);
        }
        for(int i = 0; i < numberOfCells*numberOfCells;i+=numberOfCells){
          String tempPathToImage = validationCellGrid.get(i).getPathToImage();
          validationCellGrid.get(i).setPathToImage(validationCellGrid.get(i+numberOfCells-1).getPathToImage());
          validationCellGrid.get(i+numberOfCells-1).setPathToImage(tempPathToImage);
        }
    }
    private void createCells() {
        ArrayList<Integer> numbersOfImage = new ArrayList<>();
        for(int i = 0; i < 9;i++){
            numbersOfImage.add(i);
        }
        for (int i = 0; i < numberOfCells * numberOfCells; i++) {
            Cell cell = new Cell(startPositionX + i / (numberOfCells) * Cell.CELL_WIDTH,
                    startPositionY + i % (numberOfCells) * Cell.CELL_HEIGHT,
                    Cell.CELL_WIDTH, Cell.CELL_HEIGHT,numbersOfImage);
            if (i == 0) cell.setTransparent(true);
            cells.add(cell);
        }
    }
    private boolean countInversions(){
        float counter = 0;
        for(int i = 0; i < numberOfCells*numberOfCells;i++){
            if(!cells.get(i).getPathToImage().equals(validationCellGrid.get(i).getPathToImage()) && !cells.get(i).isTransparent()) counter++;
        }
        System.out.println(counter);
        return (counter/2)%2==0;
    }
    private boolean validate(){
        for(int i = 0; i < numberOfCells*numberOfCells;i++){
            if(!cells.get(i).getPathToImage().equals(validationCellGrid.get(i).getPathToImage())) return false;
        }
        return true;
    }
    @Override
    public void show() {
        levelCompressedPicture = new Texture("levels/level_"+SlidePuzzle.currentLevel+"_compressed.jpg");
        compressedLevelPosition = new Vector2(Gdx.graphics.getWidth()-400,Gdx.graphics.getHeight()-250);
        ipaitLogoPosition = new Vector2(0,Gdx.graphics.getHeight()-LOGO_HEIGHT);
        branchTexture = new Texture("sprites/branch.png");
        branchPosition = new Vector2(Gdx.graphics.getWidth() / 2 - branchTexture.getWidth() / 2,
                Gdx.graphics.getHeight() - branchTexture.getHeight());
        branchColor = new Color(1f, 1f, 1f, 1f);
    }



    @Override
    public void render(float delta) {
        Vector2 mousePos = inputProcessor.getMousePos();
        ScreenUtils.clear(Color.valueOf("#d9d375"));
        batch.begin();
        branchColor.set(1f, 1f, 1f, 0.8f);

        batch.setColor(branchColor);
        batch.draw(branchTexture, branchPosition.x, branchPosition.y);
        batch.setColor(Color.WHITE);
        batch.draw(ipaitLogoTexture,ipaitLogoPosition.x,ipaitLogoPosition.y);
        batch.draw(levelCompressedPicture,compressedLevelPosition.x,compressedLevelPosition.y);
        for (Cell cell : cells) {
            if (onClickecCell == null) {
                onClickecCell = cell.check(mousePos);
            }
            if (onClickecCell != null && onClickecCell.getId() == cell.getId()) {
                cell.rotateTo(mousePos);
            }
            Cell intersectedCell = cell.isIntersect(Arrays.asList(cells.toArray()));
            if (inputProcessor.isDown() && intersectedCell == null) cell.toDefaultPostion();
            else if (inputProcessor.isDown() && intersectedCell != null) {
                cell.exchangeCells(intersectedCell,  numberOfCells,cells);
                cell.toDefaultPostion();
                intersectedCell.toDefaultPostion();
                if(validate()) isWin = true;
            }
            if (onClickecCell == null || onClickecCell.getId() != cell.getId()) {
                if(checkAndRenderTransparentCell(cell, batch))
                    cell.render(batch);
            }
        }
        if (onClickecCell != null && checkAndRenderTransparentCell(onClickecCell, batch)) {
                onClickecCell.render(batch);
        }
        batch.end();
        if(isWin){
            game.showWinScreen();
        }
    }

    private boolean checkAndRenderTransparentCell(Cell cell,SpriteBatch batch){
        if(cell.isTransparent()) {
            batch.setColor(Color.WHITE);
            return false;
        }
        return true;
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

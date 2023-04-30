package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;

public class SlidePuzzle extends ApplicationAdapter {

	private SpriteBatch batch;
	public static Cell onClickecCell;
	private final Array<Cell> cells = new Array<>();
	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();
	private Texture boxImg;

	@Override
	public void create() {
		batch = new SpriteBatch();
		boxImg = new Texture(Gdx.files.internal("box.png"));
		Gdx.input.setInputProcessor(inputProcessor);
		for(int i = 0; i < 9;i++){
			cells.add(new Cell(200+i/3*100,200+i%3*100,100,100));
		}
	}


	@Override
	public void render() {
		Vector2 mousePos = inputProcessor.getMousePos();
		ScreenUtils.clear(1,1,1,1);
		batch.begin();
		for(Cell cell: cells){
			if(onClickecCell == null){
				onClickecCell = cell.check(mousePos);
			}
			if(onClickecCell !=null && onClickecCell.getId() == cell.getId()) {
				cell.rotateTo(mousePos);
			}
			Cell intersectedCell = cell.isIntersect(Arrays.asList(cells.toArray()));
			if(!inputProcessor.isDown() && intersectedCell == null) cell.toDeafultPostion();
			else if(!inputProcessor.isDown() && intersectedCell != null) {
				cell.change(intersectedCell);
				cell.toDeafultPostion();
				intersectedCell.toDeafultPostion();
				intersectedCell.render(batch);
			}
			if(onClickecCell==null || onClickecCell.getId() != cell.getId())cell.render(batch);
		}
		if(onClickecCell != null) onClickecCell.render(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
	}
}
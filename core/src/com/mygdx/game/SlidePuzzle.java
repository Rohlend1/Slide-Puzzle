package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

	private final int currentLevel = 1;

	@Override
	public void create() {
//		ParticleEmitter emitter = new ParticleEmitter();
//		try {
//			emitter.load(Gdx.files.internal("jsontoxml.xml").reader(2024));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
		batch = new SpriteBatch();
		ImageDownloader imageDownloader = new ImageDownloader();
		imageDownloader.downloadImage("https://avatars.mds.yandex.net/i?id=192ca94af7e3d32ed0b1e116020fa95f_l-5679112-images-thumbs&n=13","image.png");
		Gdx.input.setInputProcessor(inputProcessor);
		for(int i = 0; i < Math.pow(currentLevel+2,2);i++){
			cells.add(new Cell(200+i/(currentLevel+2)*100,200+i%(currentLevel+2)*100,100,100));
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
			if(!inputProcessor.isDown() && intersectedCell == null) cell.toDefaultPostion();
			else if(!inputProcessor.isDown() && intersectedCell != null) {
				cell.exchangeCells(intersectedCell,currentLevel+2);
				cell.toDefaultPostion();
				intersectedCell.toDefaultPostion();
				intersectedCell.render(batch);
			}
			if(onClickecCell==null || onClickecCell.getId() != cell.getId())cell.render(batch);
		}
		if(onClickecCell != null) onClickecCell.render(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		for(Cell cell : cells){
			cell.dispose();
		}
	}
}
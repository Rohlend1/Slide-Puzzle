package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class SlidePuzzle extends Game {


	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();

	private final int currentLevel = 1;

	private final int numberOfCells = currentLevel+2;

	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;


	@Override
	public void create() {
		ImageDownloader imageDownloader = new ImageDownloader(new Stage());
		imageDownloader.downloadImages("https://5050-barbaratims-imageslicer-unt90271qxh.ws-eu96b.gitpod.io/download?image=image_2.png");
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(numberOfCells,inputProcessor);
		setScreen(mainMenuScreen);
	}

	public void showMainMenuScreen() {
		setScreen(mainMenuScreen);
	}

	public void showAuthorScreen(){
		setScreen(new AuthorScreen(this));
	}

	public void showGameScreen() {
		Gdx.input.setInputProcessor(inputProcessor);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		getScreen().render(delta);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		mainMenuScreen.dispose();
	}
}

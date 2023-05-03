package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class SlidePuzzle extends Game {


	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();

	private final int currentLevel = 2;

	private final int numberOfCells = currentLevel+2;

	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;

	@Override
	public void create() {


		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(numberOfCells,inputProcessor);
		setScreen(mainMenuScreen);
	}

	public void showMainMenu() {
		setScreen(mainMenuScreen);
	}

	public void showAuthorsScreen(){}

	public void showGameScreen() {
		Gdx.input.setInputProcessor(inputProcessor);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		// Отрисовка текущего экрана
		getScreen().render(delta);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		mainMenuScreen.dispose();
	}
}

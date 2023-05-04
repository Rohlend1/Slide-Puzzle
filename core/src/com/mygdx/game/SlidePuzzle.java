package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.scenes.AuthorScreen;
import com.mygdx.game.scenes.GameScreen;
import com.mygdx.game.scenes.MainMenuScreen;
import com.mygdx.game.scenes.WinScreen;


public class SlidePuzzle extends Game {


	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();

	private int currentLevel = 1;

	private int numberOfCells = currentLevel+2;
	private ImageDownloader imageDownloader;

	private MainMenuScreen mainMenuScreen;


	@Override
	public void create() {
		imageDownloader = new ImageDownloader(new Stage());
		imageDownloader.downloadImages("https://5050-barbaratims-imageslicer-feydmkx2jws.ws-eu96b.gitpod.io/getImage?image=image_"+currentLevel+".jpg&col=3&row=3");

		mainMenuScreen = new MainMenuScreen(this);
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
		setScreen(new GameScreen(numberOfCells,inputProcessor,this));
	}

	public void showWinScreen(){
		setScreen(new WinScreen(this));
	}

	public void nextLevel(){
		currentLevel+=1;
		imageDownloader.downloadImages("https://5050-barbaratims-imageslicer-feydmkx2jws.ws-eu96b.gitpod.io/getImage?image=image_"+currentLevel+".jpg&col=3&row=3");
		showGameScreen();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		getScreen().render(delta);
	}

	@Override
	public void dispose() {
		mainMenuScreen.dispose();
	}
}

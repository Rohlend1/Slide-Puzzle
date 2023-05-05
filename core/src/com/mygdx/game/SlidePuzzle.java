package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.scenes.AuthorScreen;
import com.mygdx.game.scenes.GameScreen;
import com.mygdx.game.scenes.MainMenuScreen;
import com.mygdx.game.scenes.WinScreen;
import com.mygdx.game.util.DirectoryCleaner;
import com.mygdx.game.util.ImageDownloader;


public class SlidePuzzle extends Game {


	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();

	public static int currentLevel = 3;

	private final int numberOfCells = 3;
	public static ImageDownloader imageDownloader;

	private MainMenuScreen mainMenuScreen;


	@Override
	public void create() {
		imageDownloader = new ImageDownloader();
		imageDownloader.downloadImages("https://5050-barbaratims-imageslicer-7z4zsh6jp7r.ws-eu96b.gitpod.io/getImage?image=level_"+currentLevel+".jpg&cols=3&rows=3");
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
		currentLevel+=1;
		System.out.println(currentLevel);
		DirectoryCleaner.clearDirectory("images");
		imageDownloader.downloadImages("https://5050-barbaratims-imageslicer-7z4zsh6jp7r.ws-eu96b.gitpod.io/getImage?image=level_"+currentLevel+".jpg&cols=3&rows=3");		setScreen(new WinScreen(this));
	}

	public void nextLevel(){
		if(currentLevel==4) showMainMenuScreen();
		else showGameScreen();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		getScreen().render(delta);
	}

	@Override
	public void dispose() {
		mainMenuScreen.dispose();
		imageDownloader.dispose();
		mainMenuScreen.dispose();
	}
}

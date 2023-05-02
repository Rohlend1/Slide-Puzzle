package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;


public class SlidePuzzle extends Game {


	private final Array<Cell> cells = new Array<>();
	private final KeyboardAdapter inputProcessor = new KeyboardAdapter();

	private final int currentLevel = 1;

	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private Skin skin;

	@Override
	public void create() {

		ImageDownloader imageDownloader = new ImageDownloader();
		imageDownloader.downloadImage("https://avatars.mds.yandex.net/i?id=192ca94af7e3d32ed0b1e116020fa95f_l-5679112-images-thumbs&n=13", "image.png");
		Gdx.input.setInputProcessor(inputProcessor);
		skin = new Skin();

		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_up.png"))));
		buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_down.png"))));
		buttonStyle.font = new BitmapFont(); // Здесь можно установить собственный шрифт

		// Добавление стиля в скин
		skin.add("default", buttonStyle);// Установка цвета текста кнопки

		// Установка стиля для кнопки в скине
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(currentLevel,inputProcessor);
		setScreen(mainMenuScreen);
	}

	public void showMainMenu() {
		setScreen(mainMenuScreen);
	}

	public Skin getSkin() {
		return skin;
	}

	public void showGameScreen() {
		cells.clear();
		for (int i = 0; i < Math.pow(currentLevel + 2, 2); i++) {
			cells.add(new Cell(200 + i / (currentLevel + 2) * 100, 200 + i % (currentLevel + 2) * 100, 100, 100));
		}
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

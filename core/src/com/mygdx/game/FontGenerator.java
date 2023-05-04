package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontGenerator {
    public static BitmapFont generateFont(int size,String pathToFont){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(pathToFont));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        font.setColor(Color.valueOf("#1b2021"));
        generator.dispose();
        return font;
    }
}

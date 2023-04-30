package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class KeyboardAdapter extends InputAdapter {

    private boolean isDown;
    private final Vector2 direction = new Vector2();
    private final Vector2 mousePos = new Vector2(-128,-128);


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDown = false;
        SlidePuzzle.onClickecCell = null;
        mousePos.set(-128,-128);
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY,int point) {
        mousePos.set(screenX,Gdx.graphics.getHeight()-screenY);
        isDown = true;
        return false;
    }


    public Vector2 getMousePos() {
        return mousePos;
    }

    public boolean isDown() {
        return isDown;
    }
}

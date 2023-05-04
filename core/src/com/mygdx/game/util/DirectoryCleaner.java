package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class DirectoryCleaner {

    public static void clearDirectory(String directoryPath) {
        FileHandle directory = Gdx.files.local(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            FileHandle[] files = directory.list();
            for (FileHandle file : files) {
                file.delete();
            }
        }
    }
}


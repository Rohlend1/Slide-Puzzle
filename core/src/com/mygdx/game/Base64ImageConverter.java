package com.mygdx.game;

import java.util.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Base64ImageConverter {
    public static void convert(String base64Image,int count) {

        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            Path path = Paths.get("image"+count+".jpg");
            Files.write(path, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

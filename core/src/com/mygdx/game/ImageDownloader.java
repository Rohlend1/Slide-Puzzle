package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageDownloader {

    private final OkHttpClient client;
    private final List<JsonTranscription> images;
    private final Stage stage;
    private final SpriteBatch batch;

    public ImageDownloader(Stage stage) {
        this.stage = stage;
        this.batch = (SpriteBatch) stage.getBatch();
        this.client = new OkHttpClient();
        this.images = new ArrayList<>();
    }

    public void downloadImages(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("Successful");
                    final String jsonResponse = response.body().string();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            processImages(jsonResponse);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failure");
                e.printStackTrace();
            }
        });
    }

    private void processImages(String jsonResponse) {
        Gson gson = new Gson();
        JsonTranscription[] imageDataArray = gson.fromJson(jsonResponse, JsonTranscription[].class);
        images.addAll(Arrays.asList(imageDataArray));
        int count = 0;

        for (JsonTranscription imageData : images) {
            Base64ImageConverter.convert(imageData.getUrl(),count++);
        }
    }

    public void dispose() {
        client.dispatcher().cancelAll();
    }
}

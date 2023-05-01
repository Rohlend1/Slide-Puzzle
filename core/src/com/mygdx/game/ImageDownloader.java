package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class ImageDownloader {
    public void downloadImage(String url, final String imagePath) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(url);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    byte[] result = httpResponse.getResult();
                    Pixmap pixmap = new Pixmap(result, 0, result.length);
                    FileHandle file = Gdx.files.local(imagePath);
                    PixmapIO.writePNG(file, pixmap);
                    pixmap.dispose();
                    Gdx.app.log("ImageDownloader", "Image downloaded successfully");
                } else {
                    Gdx.app.error("ImageDownloader", "Failed to download image. Status code: " + httpResponse.getStatus().getStatusCode());
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("ImageDownloader", "Failed to download image", t);
            }

            @Override
            public void cancelled() {
                Gdx.app.error("ImageDownloader", "Image download cancelled");
            }
        });
    }
}

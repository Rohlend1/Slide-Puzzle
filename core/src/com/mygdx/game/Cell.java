package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class Cell extends Rectangle {

    private static int count = 0;
    private final int id;
    private final float size = 428;
    private final float halfSize = size/2;
    private boolean onClick = false;
    public static final int CELL_WIDTH = 360;
    public static final int CELL_HEIGHT = 428;

    private final Vector2 defaultPos;
    private Texture texture;

    public Cell(float x, float y, int width, int height) {
        super(x,y,width,height);
        defaultPos = new Vector2(x,y);
        System.out.println(count);
        this.texture = createTextureWithBorder("images/image"+count+".jpg", 4);
        id = ++count;
    }

    private Texture createTextureWithBorder(String imagePath, int borderWidth) {
        Texture originalTexture = new Texture(imagePath);
        TextureData textureData = originalTexture.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap originalPixmap = textureData.consumePixmap();

        int newWidth = originalPixmap.getWidth() + borderWidth * 2;
        int newHeight = originalPixmap.getHeight() + borderWidth * 2;
        Pixmap borderPixmap = new Pixmap(newWidth, newHeight, originalPixmap.getFormat());

        borderPixmap.setColor(0f, 0f, 0f, 1f);
        borderPixmap.fill();
        borderPixmap.drawPixmap(originalPixmap, borderWidth, borderWidth);

        return new Texture(borderPixmap);
    }
    public void render(Batch batch){
        batch.draw(texture,this.x,this.y);
    }
    public void dispose(){
        texture.dispose();
    }

    public void rotateTo(Vector2 mousePos) {
        if(mousePos.x != -128 && mousePos.y != -128)
            this.setPosition(mousePos.x-halfSize,mousePos.y-halfSize);
    }

    public Cell check(Vector2 mousePos){
        onClick = this.contains(mousePos);
        return onClick? this:null;
    }

    public void toDefaultPostion(){
        onClick=false;
        this.setPosition(defaultPos);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "id=" + id +
                ", texture=" + texture +
                '}';
    }

    public Cell isIntersect(List<Cell> cells){
        Vector2 center = new Vector2(this.x+halfSize,this.y+halfSize);
        for(Cell cell: cells){
            if(cell.contains(center) && cell.id != this.id) return cell;
        }
        return null;
    }

    public void exchangeCells(Cell intersectedCell,int numberOfCells){
        if(Math.abs(intersectedCell.id-id) != numberOfCells && Math.abs(intersectedCell.id-id) != 1) return;
        TextureData temp = texture.getTextureData();
        texture = new Texture(intersectedCell.texture.getTextureData());
        intersectedCell.texture = new Texture(temp);
    }
    public int getId() {
        return id;
    }
}

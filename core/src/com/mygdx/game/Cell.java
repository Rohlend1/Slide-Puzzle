package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;


public class Cell extends Rectangle {

    private static int count = 0;
    private final int id;
    private final float size = 205;
    private final float halfSize = size/2;
    private boolean onClick = false;
    public static final int CELL_WIDTH = 205;
    public static final int CELL_HEIGHT = 205;
    private boolean isTransparent = false;

    private Vector2 defaultPos;
    private final Texture texture;
    private String pathToImage;
    public static final int BORDER_WIDTH = 4;

    public Cell(float x, float y, int width, int height, ArrayList<Integer> numbersOfImage) {
        super(x,y,width,height);
        defaultPos = new Vector2(x,y);
        int randInt = (int)(Math.random()*numbersOfImage.size());
        pathToImage = "images/image"+numbersOfImage.get(randInt)+".jpg";
        this.texture = createTextureWithBorder(pathToImage);
        numbersOfImage.remove(randInt);
        id = count++;
    }

    public Cell(int id,float x, float y, int width, int height,String pathToImage) {
        super(x,y,width,height);
        this.id = id;
        this.defaultPos = new Vector2(x,y);
        this.pathToImage = pathToImage;
        texture = createTextureWithBorder(pathToImage);
    }

    private static Texture createTextureWithBorder(String imagePath) {
        Texture originalTexture = new Texture(imagePath);
        TextureData textureData = originalTexture.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap originalPixmap = textureData.consumePixmap();

        int newWidth = originalPixmap.getWidth() + BORDER_WIDTH * 2;
        int newHeight = originalPixmap.getHeight() + BORDER_WIDTH * 2;
        Pixmap borderPixmap = new Pixmap(newWidth, newHeight, originalPixmap.getFormat());

        borderPixmap.setColor(0f, 0f, 0f, 1f);
        borderPixmap.fill();
        borderPixmap.drawPixmap(originalPixmap, BORDER_WIDTH, BORDER_WIDTH);

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
                ", pathToImage='" + pathToImage + '\'' +
                '}';
    }

    public Cell isIntersect(List<Cell> cells){
        Vector2 center = new Vector2(this.x+halfSize,this.y+halfSize);
        for(Cell cell: cells){
            if(cell.contains(center) && cell.id != this.id) return cell;
        }
        return null;
    }

    public void exchangeCells(Cell intersectedCell,int numberOfCells,Array<Cell> cells){
        if((!intersectedCell.isTransparent && !this.isTransparent)
                ||(Math.abs(cells.indexOf(intersectedCell,false)-cells.indexOf(this,false)) != numberOfCells &&
                Math.abs(cells.indexOf(intersectedCell,false) -cells.indexOf(this,false)) != 1)) return;
        int tempIndexIntersected = cells.indexOf(intersectedCell,false);
        int tempIndexCell = cells.indexOf(this,false);
        cells.set(tempIndexIntersected,this);
        cells.set(tempIndexCell,intersectedCell);

        Vector2 tempVector = intersectedCell.getDefaultPos();
        intersectedCell.setDefaultPos(this.getDefaultPos());
        this.setDefaultPos(tempVector);
    }

    public void setTransparent(boolean transparent) {
        isTransparent = transparent;
    }

    public int getId() {
        return id;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
    public Vector2 getDefaultPos() {
        return defaultPos;
    }

    public void setDefaultPos(Vector2 defaultPos) {
        this.defaultPos = defaultPos;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class Cell extends Rectangle {

    private static int count = 0;
    private final int id;
    private final float size = 100;
    private final float halfSize = size/2;
    private boolean onClick = false;

    private final Vector2 defaultPos;
    private Texture texture;

    public Cell(float x, float y, int width, int height) {
        super(x,y,width,height);
        defaultPos = new Vector2(x,y);
        id = ++count;
        if(id==1) this.texture = new Texture("box"+id+".png");
        else if(id==2) this.texture = new Texture("box"+id+".png");
        else this.texture = new Texture("box.png");
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
                ", onClick=" + onClick +
                '}';
    }
    public Cell isIntersect(List<Cell> cells){
        Vector2 center = new Vector2(this.x+halfSize,this.y+halfSize);
        for(Cell cell: cells){
            if(cell.contains(center) && cell.id != this.id) return cell;
        }
        return null;
    }

    public void exchangeCells(Cell intersectedCell,int currentLevel){
        if(Math.abs(intersectedCell.id-id) != currentLevel && Math.abs(intersectedCell.id-id) != 1) return;
        TextureData temp = texture.getTextureData();
        texture = new Texture(intersectedCell.texture.getTextureData());
        intersectedCell.texture = new Texture(temp);
    }

    public int getId() {
        return id;
    }
}

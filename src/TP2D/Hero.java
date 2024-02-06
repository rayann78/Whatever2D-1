package TP2D;

import java.io.File;

import javax.imageio.ImageIO;

public final class Hero extends DynamicThings{
    private static volatile Hero instance = null;
    private static final int initialX = 120;
    private static final int initialY = 120;
    private final int MAX_HEALTH = 250;

    private Hero() {
        super(initialX, initialY, 40,40, 0);
        try{this.setImage(ImageIO.read(new File("img/heroTileSheet.png")));}
        catch (Exception e){
            e.printStackTrace();
        }
        super.MAX_HEALTH = this.MAX_HEALTH;
        health=MAX_HEALTH;
    }

    public final static Hero getInstance() {
        if (Hero.instance == null) {
            synchronized(Hero.class) {
                if (Hero.instance == null) {
                    Hero.instance = new Hero();
                }
            }
        }
        return Hero.instance;
    }

    public String toString(){
        return "Hero";
    }
    
    public void reset(){
        super.reset();
        x=initialX;
        y=initialY;
        this.hitBox = new HitBox(x, y, width, height);
    }
}
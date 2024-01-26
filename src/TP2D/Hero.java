package TP2D;

import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;

public final class Hero extends DynamicThings{

    private static volatile Hero instance = null;
    private Orientation orientation=Orientation.RIGHT;
    private boolean isWalking = false;
    private Attack attack = new Attack(48, 48);

    private Hero() {
        super(120, 120, 40,40);
        try{this.setImage(ImageIO.read(new File("img/heroTileSheet.png")));}
        catch (Exception e){
            e.printStackTrace();
        }
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

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    @Override
    public void draw(Graphics g){
        int attitude = orientation.getI();
        int index = (int) ((System.currentTimeMillis()/125)%10);
        index=isWalking?index:0;
        g.drawImage(image,(int)x,(int)y,(int)x+width,(int) y+ height,index*96,100*attitude,(index+1)*96,100*(attitude+1),null,null);

        int dx=0, dy=0;
        switch (getOrientation()) {
            case UP:
                dx = 0;
                dy = -height;
                break;
            case DOWN:
                dx = 0;
                dy = height;
                break;
            case LEFT:
                dx = -width;
                dy = 0;
                break;
            case RIGHT:
                dx = width;
                dy = 0;
                break;
        }

        if(isAttacking()){
            attack.draw(g, attitude, (int)(x + dx), (int)(y + dy));
        }
    }

    public void attack(){
        setWalking(false);
        attack.startAttack();
    }

    public boolean isAttacking(){
        return attack.isAttacking;
    }
}
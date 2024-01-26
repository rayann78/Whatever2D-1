package TP2D;

import java.awt.Image;

public class DynamicThings extends AnimatedThings{
    public DynamicThings(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public DynamicThings(int x, int y, Image image) {
        super(x, y, image);
    }

    public void move(double moveX, double moveY){
        this.x=this.x+moveX;
        this.y=this.y+moveY;
    }

    public void setImage(Image image){
        this.image=image;
    }

    protected boolean checkIfMovePossible(double dx, double dy, Dungeon dungeon) {
        return checkIfMovePossibleAndNotCheckOfPlayer(dx, dy, dungeon);
    }

    protected boolean checkIfMovePossible(double dx, double dy, Dungeon dungeon, boolean checkForPlayer) {
        boolean result = checkIfMovePossibleAndNotCheckOfPlayer(dx, dy, dungeon);
        if(checkForPlayer && Hero.getInstance() != this) {
            if (Hero.getInstance().getHitBox().intersect(hitBox)) {
                return false;
            }
        }
        return result;
    }  

    private boolean checkIfMovePossibleAndNotCheckOfPlayer(double dx, double dy, Dungeon dungeon) {
        boolean movePossible = true;
        HitBox hitBox = new HitBox(this.getHitBox().getX() + dx, this.getHitBox().getY() + dy, this.getHitBox().getWidth(), this.getHitBox().getHeight());

        // Check if move is outside of the map
        if (    x + dx < 0 
                || x + dx > dungeon.getWidth() * width 
                || y + dy < 0 
                || y + dy > dungeon.getHeight() * height) 
        {
            movePossible = false;
        }

        for (Things things : dungeon.getRenderList()) {
            if (things instanceof SolidThings) {
                if (((SolidThings) things).getHitBox().intersect(hitBox)) {
                    movePossible = false;
                    break;
                }
            }
        }

        for (DynamicThings things : dungeon.getNPCList()) {
            if (things != this) {
                if (((SolidThings) things).getHitBox().intersect(hitBox)) {
                    movePossible = false;
                    break;
                }
            }
        }

        return movePossible;
    }

    // Returns if move has been successful
    public boolean moveIfPossible(double dx, double dy, Dungeon dungeon) {
        if (checkIfMovePossible(dx, dy, dungeon)) {
            this.x = x + dx;
            this.y = y + dy;
            this.getHitBox().move(dx, dy);
            return true;
        } else {
            return false;
        }
    }
}

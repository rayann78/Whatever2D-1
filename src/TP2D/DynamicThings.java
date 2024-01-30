package TP2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class DynamicThings extends AnimatedThings{
    protected ArrayList<DynamicThings> possibleTargets = new ArrayList<>();
    protected int MAX_HEALTH = 100;
    protected int health = MAX_HEALTH;
    protected Orientation orientation=Orientation.RIGHT;
    protected boolean isWalking = false;
    protected Attack attack = new Attack(48, 48);
    protected boolean isDead = false;
    protected HealthBar healthBar;
    
    public DynamicThings(int x, int y, int width, int height, int id) {
        super(x, y, width, height);
        Color colorHealthBar;
        String label;
        if (id == 0) {
            colorHealthBar = Color.GREEN;
            label = "Hero";
        } else {
            colorHealthBar = Color.BLACK;
            label = "Monster";
        }
        healthBar = new HealthBar(id, MAX_HEALTH, this, colorHealthBar, label);
    }

    public DynamicThings(int x, int y, Image image, int id) {
        super(x, y, image);
        Color colorHealthBar;
        String label;
        if (id == 0) {
            colorHealthBar = Color.GREEN;
            label = "Hero";
        } else {
            colorHealthBar = Color.BLACK;
            label = "Monster";
        }
        healthBar = new HealthBar(id, MAX_HEALTH, this, colorHealthBar, label);
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

    public void addPossibleTarget(DynamicThings target){
        possibleTargets.add(target);
    }

    public void takeDamage(int damage){
        this.health-=damage;
        if (this.health<=0){
            health=0;
            this.die();
        }
        System.out.println(this + " health: " + this.health);
    }

    public void die() {
        isDead = true;
        this.hitBox = new HitBox(0, 0, 0, 0);
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
        if(isDead){
            return;
        }

        int attitude = orientation.getI();
        int index = (int) ((System.currentTimeMillis()/125)%10);
        index=isWalking?index:0;
        g.drawImage(image,(int)x,(int)y,(int)x+width,(int) y+ height,index*96,100*attitude,(index+1)*96,100*(attitude+1),null,null);

        if(isAttacking()){
            attack.draw(g);
        }

        healthBar.draw(g);
    }

    public void attack(){
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

        setWalking(false);
        attack.startAttack(possibleTargets, x + dx, y + dy);
    }

    public boolean isAttacking(){
        return attack.isAttacking;
    }

    public void reset(){
        super.reset((int)x,(int)y,width,height);
        isDead=false;
        health=MAX_HEALTH;
    }

    public int getCurrentHealth() {
        return health;
    }
}

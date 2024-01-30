package TP2D;

import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Monster extends DynamicThings {
    private int initialX = 120;
    private int initialY = 120;

    public Monster(int x, int y, int width, int height, int id) {
        super(x, y, width, height, id);
        initialX = x;
        initialY = y;
        try {
            this.setImage(ImageIO.read(new File("img/monsterTileSheet.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        health = MAX_HEALTH;
        attack = new Attack(60, 60);
    }

    public void action(Dungeon dungeon, int speed) {
        if (isDead) {
            return;
        }
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
        HitBox attackHitBox = new HitBox(x + dx, y + dy, attack.getWidth(), attack.getHeight());
        // If hero is on a tile next to the monster, attack the hero
        if(Hero.getInstance().getHitBox().intersect(attackHitBox)) {
            attack();
        } else {
            double[] move = new double[2];
            if (hero_in_view(dungeon)) {
                move = move_toward_hero(dungeon, speed);
            } else {
                move = move_randomly(dungeon, speed);
            }
            moveIfPossible(move[0], move[1], dungeon);
        }
    }

    private boolean hero_in_view(Dungeon dungeon) {
        int dx = 0, dy = 0;
        switch(getOrientation()) {
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
        int cur_x = dx, cur_y = dy;
        while (checkIfMovePossible(cur_x, cur_y, dungeon, false)) {
            HitBox viewHitBox = new HitBox(x + cur_x, y + cur_y, width*1.5, height*1.5);
            if (Hero.getInstance().getHitBox().intersect(viewHitBox)) {
                return true;
            }
            cur_x += dx;
            cur_y += dy;
        }
        return false;
    }

    private double[] move_toward_hero(Dungeon dungeon, int speed) {
        double dx = 0, dy = 0;
        if (Hero.getInstance().getHitBox().getX() < this.x) {
            dx = -speed;
        } else if (Hero.getInstance().getHitBox().getX() > this.x) {
            dx = speed;
        }
        
        if (Hero.getInstance().getHitBox().getY() < this.y) {
            dy = -speed;
        } else if (Hero.getInstance().getHitBox().getY() > this.y) {
            dy = speed;
        }

        return new double[]{dx, dy};
    }

    private double[] move_randomly(Dungeon dungeon, int speed) {
        // First check if we can continue in same orientation
        double dx = 0, dy = 0;
        switch(getOrientation()) {
            case UP:
                dx = 0;
                dy = -speed;
                break;
            case DOWN:
                dx = 0;
                dy = speed;
                break;
            case LEFT:
                dx = -speed;
                dy = 0;
                break;
            case RIGHT:
                dx = speed;
                dy = 0;
                break;
        }

        if (checkIfMovePossible(dx, dy, dungeon)) {
            moveIfPossible(dx, dy, dungeon);
            return new double[]{0, 0};
        }
        double [][] moves = {
            {0, -speed},
            {0, speed},
            {-speed, 0},
            {speed, 0}
        };

        double[][] possibleMoves = {};
        for (double[] move : moves) {
            if (checkIfMovePossible(move[0], move[1], dungeon)) {
                double[][] temp = Arrays.copyOf(possibleMoves, possibleMoves.length + 1);
                possibleMoves = temp;
                possibleMoves[possibleMoves.length - 1] = move;
            }
        }

        // Select a random move in possibleMoves
        if (possibleMoves.length > 0) {
            double[] move = possibleMoves[(int)(Math.random() * possibleMoves.length)];

            // Convert move to orientation
            if (move[0] == 0) {
                if (move[1] < 0) {
                    orientation = Orientation.UP;
                } else {
                    orientation = Orientation.DOWN;
                }
            } else {
                if (move[0] < 0) {
                    orientation = Orientation.LEFT;
                } else {
                    orientation = Orientation.RIGHT;
                }
            }
            return move;
        }
        return new double[]{0, 0};
    }

    public String toString() {
        return "Monster";
    }

    public void reset() {
        x = initialX;
        y = initialY;
        this.hitBox = new HitBox(x, y, width, height);
    }

    public void die() {
        super.die();
    }
}

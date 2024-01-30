package TP2D;

import java.awt.Graphics;
import java.awt.Image;

public class AnimatedThings extends SolidThings {
    public AnimatedThings(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public AnimatedThings(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void draw(Graphics g){

    }

    public void reset(int x, int y, int width, int height) {
        super.reset(x,y,width,height);
    }
}

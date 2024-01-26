package TP2D;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GameRender extends JPanel {
    private Dungeon dungeon;
    private Hero hero;

    public GameRender(Dungeon dungeon, DynamicThings hero) {
        this.dungeon = dungeon;
        this.hero = Hero.getInstance();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Things t : dungeon.getRenderList()){
            t.draw(g);
        }
        for (DynamicThings d : dungeon.getNPCList()){
            d.draw(g);
        }
        hero.draw(g);
    }
}

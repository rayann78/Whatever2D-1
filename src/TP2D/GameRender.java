package TP2D;

import java.awt.Graphics;
import java.util.Iterator;

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
        Iterator<DynamicThings> iterator = dungeon.getNPCList().iterator();
        while (iterator.hasNext()) {
            DynamicThings d = iterator.next();
            if (!d.isDead) {
                d.draw(g);
            } else {
                iterator.remove();
            }
        }
        hero.draw(g);
    }
}

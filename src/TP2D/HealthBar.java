package TP2D;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxHealth;
    private DynamicThings character; // Reference to the character
    private Color color;
    private String label;

    public HealthBar(int y, int maxHealth, DynamicThings character, Color color, String label) {
        this.x = 0;
        this.y = y*10;
        this.width = maxHealth;
        this.height = 10;
        this.maxHealth = maxHealth;
        this.character = character;
        this.color = color;
        this.label = label;
    }

    public void draw(Graphics g) {        
        g.setColor(color);
        int healthBarWidth = (int) ((double) character.getCurrentHealth() / maxHealth * width);
        g.fillRect(x, y, healthBarWidth, height);
        
        // Determine the contrasting color for the label
        Color labelColor = getContrastingColor(color);
        g.setColor(labelColor);
        g.drawString(label, x, y + height);
    }

    private Color getContrastingColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int luminance = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
        
        return luminance > 128 ? Color.BLACK : Color.WHITE;
    }
}
package TP2D;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TileManager {
    private final int width;
    private final int heigth;

    private Image[][] tiles;
    private BufferedImage tileSheet;

    public TileManager(int width, int height) {
        this.width = width;
        this.heigth = height;
        setTiles(width, height, "./img/tileSetTest.png");
    }

    public TileManager(int width, int height, String fileName){
        this.width = width;
        this.heigth = height;
        setTiles(width, height, fileName);
    }

    private void setTiles(int width, int height, String fileName){
        try{
            tileSheet = ImageIO.read(new File(fileName));}
        catch (Exception e){
            e.printStackTrace();
        }
        tiles = new Image[tileSheet.getWidth()/width][tileSheet.getHeight()/height];
        for(int y = 0; index_tileSheet_matching(y) + height < tileSheet.getHeight(); y = y + 1) {
            for (int x = 0; index_tileSheet_matching(x) + width < tileSheet.getWidth(); x = x + 1) {
                tiles[x][y] = tileSheet.getSubimage(index_tileSheet_matching(x), index_tileSheet_matching(y), width, height);
            }
        }
    }

    private int index_tileSheet_matching(int a){
        return 51*a+3;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public Image getTile(int x, int y){
        return tiles[x][y];
    }
}

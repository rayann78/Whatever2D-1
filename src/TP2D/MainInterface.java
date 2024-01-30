package TP2D;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MainInterface extends JFrame implements KeyListener {
    TileManager tileManager = new TileManager(48,48,"./img/tileSet.png");
    Dungeon dungeon = new Dungeon("./gameData/level1.txt",tileManager);
    Hero hero = Hero.getInstance();
    GameRender panel = new GameRender(dungeon,hero);
    private Clip backgroundMusic;

    public MainInterface() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
        this.setVisible(true);
        this.setSize(new Dimension(dungeon.getWidth()* tileManager.getWidth(), dungeon.getHeight()*tileManager.getHeigth()));
        this.addKeyListener(this);

        ActionListener animationTimer=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                final int speed=10;
                if (hero.isWalking()){
                    switch (hero.getOrientation()){
                        case LEFT:  hero.moveIfPossible(-speed,0,dungeon);
                                    break;
                        case RIGHT: hero.moveIfPossible(speed,0,dungeon);
                                    break;
                        case UP:    hero.moveIfPossible(0,-speed,dungeon);
                                    break;
                        case DOWN:  hero.moveIfPossible(0,speed,dungeon);
                                    break;

                    }
                }

                if (hero.isDead) {
                    GameOverMenu gameOverMenu = new GameOverMenu(false);
                    gameOverMenu.setVisible(true);
                    restart();
                } else if (dungeon.hasWon()) {
                    GameOverMenu gameOverMenu = new GameOverMenu(true);
                    gameOverMenu.setVisible(true);
                    restart();
                }

                dungeon.updateNPCs(speed);
            }
        };
        Timer timer = new Timer(50,animationTimer);
        timer.start();

        loadBackgroundMusic("./music/music.wav");
        playBackgroundMusic();
    }

    public static void main(String[] args){
        TitleScreen titleScreen = new TitleScreen();
        titleScreen.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(hero.isAttacking()){
            return;
        }
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                hero.setOrientation(Orientation.LEFT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setOrientation(Orientation.RIGHT);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_UP:
                hero.setOrientation(Orientation.UP);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_DOWN:
                hero.setOrientation(Orientation.DOWN);
                hero.setWalking(true);
                break;
            case KeyEvent.VK_SPACE:
                hero.attack();
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hero.setWalking(false);
    }

    private void loadBackgroundMusic(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                return;
            }

            backgroundMusic = (Clip) AudioSystem.getLine(info);
            backgroundMusic.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void restart() {
        dungeon.reset();
        hero.reset();
    }
}

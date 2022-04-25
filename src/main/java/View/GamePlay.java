package View;

import Main.GlobalVars;
import Main.MainRunnable;
import Model.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel {
    // useful global variables
    private boolean play = false;
    private long score = 0;
    private Level level;

    // Timer
    private Timer timer;
    private int delay; // <- it modifies the ball speed (aka the update time of each frame)

    // paddle
    private int playerX = GlobalVars.frameWidth / 2 - 200;

    // position of ball
    private int ballposX;
    private int ballposY;
    private int ballXdir = -1;
    private int ballYdir = -2;

    public GamePlay(Level level) {
        this.level = level;
        this.delay = (int) level.getBallSpeed();

        generateBallPosition();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !play) {
                    play = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    play = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && play) {
                    if (playerX >= GlobalVars.frameWidth - 400) {
                        playerX = GlobalVars.frameWidth - 410;
                    } else {
                        moveRight();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && play) {
                    if (playerX < 10) {
                        playerX = 10;
                    } else {
                        moveLeft();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !play) {

                }
            }
        });

        setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

        timer = new Timer(delay, e -> {
            timer.start();

            if (!play) {
                return;
            }
            // check ball collision with the paddle
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, GlobalVars.frameHeight - 32 - 8, 400, 8))
                    && ballYdir > 0) {
                ballYdir = -ballYdir;

                playSound("sounds/hit_paddle.wav");
            }

            // check map collision with the ball
            for (int i = 0; i < level.getMap().length; i++) {
                for (int j = 0; j < level.getMap()[0].length; j++) {
                    if (level.getMap()[i][j] != 0) {
                        int brickX = j * GlobalVars.brickWidth + 80;
                        int brickY = i * GlobalVars.brickHeight + 50;

                        Rectangle brickRect = new Rectangle(brickX, brickY, GlobalVars.brickWidth, GlobalVars.brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            setBrickValue(0, i, j);
                            score += 5;
                            level.removeBrick();

                            playSound("sounds/hit.wav");

                            // when ball hit right or left of brick
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else { // when ball hits top or bottom of brick
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            // check ball collision with border left
            if (ballposX < 0) {
                ballXdir = -ballXdir;

                playSound("sounds/hit_wall.wav");
            }

            // check ball collision with border top
            if (ballposY < 0) {
                ballYdir = -ballYdir;

                playSound("sounds/hit_wall.wav");
            }

            // check ball collision with border right
            if (ballposX > GlobalVars.frameWidth - 3 - 20) {
                ballXdir = -ballXdir;

                playSound("sounds/hit_wall.wav");
            }

            repaint();
        });
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, GlobalVars.frameWidth, GlobalVars.frameHeight);

        // drawing map
        draw((Graphics2D) g);

        // borders
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 3, GlobalVars.frameHeight);
        g.fillRect(0, 0, GlobalVars.frameWidth, 3);
        g.fillRect(GlobalVars.frameWidth - 3, 0, 3, GlobalVars.frameHeight);

        // the scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, GlobalVars.frameWidth - 64, 30);

        // the paddle
        g.setColor(Color.WHITE);
        g.fillRect(playerX, GlobalVars.frameHeight - 8 - 32, 400, 8);

        // the ball
        g.setColor(Color.WHITE);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(!play) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi SPACE per iniziare a giocare!", GlobalVars.frameWidth / 2, GlobalVars.frameHeight / 2);
        }

        // when you won the game
        if (level.getBreakBricks() <= 0) {
            play = false;

            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Hai vinto!", GlobalVars.frameWidth / 2, GlobalVars.frameHeight / 2);

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi ESC per tornare alla selezione dei livelli", GlobalVars.frameWidth / 2, GlobalVars.frameHeight / 2 + 40);

            level.setDone(true);
        }

        // when you lose the game
        if (ballposY > GlobalVars.frameHeight) {
            play = false;

            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Hai perso! Score: " + score, GlobalVars.frameWidth / 2, GlobalVars.frameHeight / 2);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi ESC per tornare alla selezione dei livelli", GlobalVars.frameWidth / 2, GlobalVars.frameHeight / 2 + 40);

            timer.stop();
        }

        g.dispose();
    }


    // Private methods

    private void moveLeft() {
        play = true;
        playerX -= 15;
    }

    private void moveRight() {
        play = true;
        playerX += 15;
    }

    private void generateBallPosition() {
        ballposX = (int) (Math.random() * GlobalVars.playAreaWidth) + 20;
        ballposY = (int) (Math.random() * GlobalVars.playAreaHeight) + 500;
    }

    private void playSound(String soundPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(MainRunnable.class.getClassLoader().getResourceAsStream(soundPath));
            clip.open(inputStream);
            clip.start();
        } catch (Exception xe) {
            System.err.println(xe.getMessage());
        }
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < level.getMap().length; i++) {
            for (int j = 0; j < level.getMap()[0].length; j++) {
                if (level.getMap()[i][j] != 0) { // Create the white box if isn't already broken
                    g.setColor(Color.WHITE);

                    g.fillRect(j * GlobalVars.brickWidth + 80, i * GlobalVars.brickHeight + 50, GlobalVars.brickWidth, GlobalVars.brickHeight);
                    // this is just to show separate brick, game can still run without it
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * GlobalVars.brickWidth + 80, i * GlobalVars.brickHeight + 50, GlobalVars.brickWidth, GlobalVars.brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        level.getMap()[row][col] = value;
    }
}

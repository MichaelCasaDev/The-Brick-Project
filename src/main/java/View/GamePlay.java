package View;

import Main.GameplayEvents;
import Main.GlobalVars;
import Model.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    // useful variables
    private boolean play = false;
    private boolean end = false;
    private long score = 0;
    private long startTime = 0;
    final private Level level;
    private GameplayEvents gameplayEvents;

    // Timer
    final private Timer timer;

    // paddle
    private int playerX = GlobalVars.frameWidth / 2 - 100;

    // position of ball
    private int ballposX;
    private int ballposY;
    private int ballXdir = -1;
    private int ballYdir = -2;

    /* ##############################
     * Game logic form here!
     * ############################## */

    public GamePlay(Level level) {
        this.level = level;
        level.reloadMap();
        int delay = (int) level.getBallSpeed(); // <- it modifies the ball speed (aka the update time of each frame)

        generateBallPosition();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(this);

        setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(GlobalVars.backgroundColor);
        g.fillRect(1, 1, GlobalVars.frameWidth, GlobalVars.frameHeight);

        // drawing map
        drawMap((Graphics2D) g);

        // borders
        g.setColor(GlobalVars.borderColor);
        g.fillRect(0, 0, 3, GlobalVars.frameHeight);
        g.fillRect(0, 0, GlobalVars.frameWidth, 3);
        g.fillRect(GlobalVars.frameWidth - 3, 0, 3, GlobalVars.frameHeight);

        // the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, GlobalVars.frameWidth - 32 - g.getFontMetrics().stringWidth("" + score), 32);

        // the paddle
        g.setColor(GlobalVars.paddleColor);
        g.fillRect(playerX, GlobalVars.frameHeight - 8 - 64, 200, 8);

        // the ball
        g.setColor(GlobalVars.ballColor);
        g.fillOval(ballposX, ballposY, 20, 20);

        // init
        if(!play) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi SPACE per iniziare a giocare!", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi SPACE per iniziare a giocare!") / 2, GlobalVars.frameHeight / 2);

            // the time (fixed)
            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("00:00", 32, 32);
        }

        // the time (dynamic)
        if(play){
            long timeSeconds = TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            long timeMinutes = TimeUnit.MINUTES.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            long realTimeSeconds = (timeSeconds >= 60 ? (timeSeconds - (timeMinutes * 60)) : timeSeconds);

            String str = (timeMinutes >= 10 ? timeMinutes : ("0" + timeMinutes)) + ":" + (realTimeSeconds >= 10 ? "" : "0") + realTimeSeconds;

            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(str, 32, 32);
        }

        // when you won the game
        if (level.getBreakBricks() <= 0) {
            play = false;
            end = true;
            level.setDone(true);

            gameplayEvents.endMenuOpen(true);
        }

        // when you lose the game
        if (ballposY > GlobalVars.frameHeight) {
            play = false;
            end = true;
            gameplayEvents.endMenuOpen(false);
        }

        g.dispose();
    }

    public void riprendi() {
        play = true;
        timer.start();
    }

    /* ##############################
     * Gameplay listener
     * ############################## */

    public void addGameplayListener(GameplayEvents gameplayEvents) {
        this.gameplayEvents = gameplayEvents;
    }

    /* ##############################
     * Private methods
     * ############################## */

    private void moveLeft() {
        play = true;
        playerX -= 15;
    }

    private void moveRight() {
        play = true;
        playerX += 15;
    }

    private void generateBallPosition() {
        ballposX = (int) (Math.random() * GlobalVars.playAreaWidth);
        ballposY = (int) (Math.random() * GlobalVars.playAreaHeight) + GlobalVars.brickHeight * GlobalVars.gameRows + 64;

        if(ballposX < GlobalVars.playAreaWidth / 2) {
            ballXdir = 1;
        }
    }

    private void playSound(String soundPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(new File(GlobalVars.dirBase + soundPath));
            clip.open(inputStream);
            clip.start();
        } catch (Exception xe) {
            System.err.println(xe.getMessage());
        }
    }

    private void drawMap(Graphics2D g) {
        for (int i = 0; i < level.getMap().length; i++) {
            for (int j = 0; j < level.getMap()[0].length; j++) {
                if (level.getMap()[i][j] != 0) { // Create the white box if isn't already broken
                    g.setColor(GlobalVars.brickColor);

                    g.fillRect(j * GlobalVars.brickWidth + 80, i * GlobalVars.brickHeight + 50, GlobalVars.brickWidth, GlobalVars.brickHeight);
                    // this is just to show separate brick, game can still run without it
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * GlobalVars.brickWidth + 80, i * GlobalVars.brickHeight + 50, GlobalVars.brickWidth, GlobalVars.brickHeight);
                }
            }
        }
    }

    private void setBrickValue(int row, int col) {
        level.getMap()[row][col] = 0;
    }

    /* ##############################
     * Override methods for keyListener and Timer
     * ############################## */

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !play && startTime == 0) {
            play = true;

            startTime = System.nanoTime();

            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && play) {
            if (playerX >= GlobalVars.frameWidth - 200) {
                playerX = GlobalVars.frameWidth - 210;
            } else {
                moveRight();
            }

            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && play) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }

            return;
        }

        // In game open menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && play && !end && startTime != 0) {
            play = false;
            timer.stop();
            gameplayEvents.inGameMenuOpen();

            return;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (!play) {
            return;
        }

        // check ball collision with the paddle
        if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, GlobalVars.frameHeight - 8 - 64, 200, 8))
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
                        setBrickValue(i, j);
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
    }
}

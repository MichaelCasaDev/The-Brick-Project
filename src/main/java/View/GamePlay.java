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
    private boolean play;
    private boolean end;

    private long score;
    private int delay;
    private long startTime;
    private String timeStr;

    // some final variables
    final private Level level;
    final private Timer timer;

    // custom events (used to show the endgame menu from the controller)
    private GameplayEvents gameplayEvents;

    // paddle
    private int paddle = GlobalVars.frameWidth / 2 - 100;

    // position of ball
    private int ballposX;
    private int ballposY;
    private int ballXdir = -1;
    private int ballYdir = -2;

    /* ##############################
     * Game logic form here!
     * ############################## */

    public GamePlay(Level level) {
        this.play = false;
        this.end = false;
        this.score = 0;
        this.startTime = -1;
        this.level = level;
        this.delay = (int) level.getBallSpeed(); // <- it modifies the ball speed (aka the update time of each frame)

        level.reloadMap();
        generateBallPosition();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(this);

        setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

        timer = new Timer(delay, this);
        timer.start();
    }

    // The main graphics method (draw everything in the screen)
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
        g.setColor(GlobalVars.scoreColor);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, GlobalVars.frameWidth - 32 - g.getFontMetrics().stringWidth("" + score), 32);

        // the paddle
        g.setColor(GlobalVars.paddleColor);
        g.fillRect(paddle, GlobalVars.frameHeight - 8 - 64, 200, 8);

        // the ball
        g.setColor(GlobalVars.ballColor);
        g.fillOval(ballposX, ballposY, 20, 20);

        // init
        if(!play && startTime == -1) {
            g.setColor(GlobalVars.initialTextColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi SPACE per iniziare a giocare!", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi SPACE per iniziare a giocare!") / 2, GlobalVars.frameHeight / 2);

            // the time (fixed)
            g.setColor(GlobalVars.timerColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("00:00", 32, 32);
        }

        // the time (dynamic) ### NOT WORKING CORRECTLY ###
        if(play){
            long timeSeconds = TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            long timeMinutes = TimeUnit.MINUTES.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            long realTimeSeconds = (timeSeconds >= 60 ? (timeSeconds - (timeMinutes * 60)) : timeSeconds);

            timeStr = (timeMinutes >= 10 ? timeMinutes : ("0" + timeMinutes)) + ":" + (realTimeSeconds >= 10 ? "" : "0") + realTimeSeconds;

            g.setColor(GlobalVars.timerColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(timeStr, 32, 32);
        }

        // when you won the game
        if (level.getBreakBricks() <= 0) {
            play = false;
            end = true;

            gameplayEvents.endMenuOpen(true);
        }

        // when you lose the game
        if (ballposY > GlobalVars.frameHeight) {
            play = false;
            end = true;
            gameplayEvents.endMenuOpen(false);
        }

        // inGame menu
        if(!play && !end && startTime != -1) {
            showInGameMenu(g);
        }

        g.dispose();
    }

    public String getTimeStr() {
        return timeStr;
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

    // Move paddle to left
    private void moveLeft() {
        play = true;
        paddle -= 15;
    }

    // Move paddle to right
    private void moveRight() {
        play = true;
        paddle += 15;
    }

    // Generate random ball position
    private void generateBallPosition() {
        ballposX = (int) (Math.random() * GlobalVars.playAreaWidth);
        ballposY = (int) (Math.random() * GlobalVars.playAreaHeight) + GlobalVars.brickHeight * GlobalVars.gameRows + 64;

        if(ballposX < GlobalVars.playAreaWidth / 2) {
            ballXdir = 1;
        }
    }

    // Play sounds
    private void playSound(String soundPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(new File(GlobalVars.dirBase + "sounds/" + soundPath));
            clip.open(inputStream);
            clip.start();
        } catch (Exception xe) {
            System.err.println(xe.getMessage());
        }
    }

    // Draw the bricks map
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

    // Set the brick value to 0 => hide it
    private void breakBrick(int row, int col) {
        level.getMap()[row][col] = 0;
    }

    // Show the in game menu (pause menu)
    private void showInGameMenu(Graphics g) {
        // the background
        g.setColor(new Color(0,0,0, 128));
        g.fillRect(1, 1, GlobalVars.frameWidth, GlobalVars.frameHeight);

        // the title
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Pausa", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Pausa")/2, 128);

        // the text #1
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 18));
        g.drawString("Premi ESC per continuare a giocare", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi ESC per continuare a giocare")/2, 256);

        // the text #2
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 18));
        g.drawString("Premi SPACE per ricominciare la partita", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi SPACE per ricominciare la partita")/2, 288);
    }

    /* ##############################
     * Override methods for keyListener and Timer
     * ############################## */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Listen to pressed keys
    @Override
    public void keyPressed(KeyEvent e) {
        // to start the game
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !play && startTime == -1) {
            play = true;

            startTime = System.nanoTime();

            return;
        }

        // move right paddle
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && play) {
            if (paddle >= GlobalVars.frameWidth - 200) {
                paddle = GlobalVars.frameWidth - 210;
            } else {
                moveRight();
            }

            return;
        }

        // move left paddle
        if (e.getKeyCode() == KeyEvent.VK_LEFT && play) {
            if (paddle < 10) {
                paddle = 10;
            } else {
                moveLeft();
            }

            return;
        }

        // In game menu open
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && play && !end && startTime != -1) {
            play = false;

            return;
        }

        // In game menu close
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !play && !end && startTime != -1) {
            play = true;

            return;
        }

        // In game menu close & restart
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !play && !end && startTime != -1) {
            this.play = false;
            this.end = false;
            this.score = 0;
            this.startTime = -1;
            this.delay = (int) level.getBallSpeed(); // <- it modifies the ball speed (aka the update time of each frame)

            level.reloadMap();
            generateBallPosition();

            return;
        }
    }

    // Run automatically thanks to the Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            // check ball collision with the paddle
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(paddle, GlobalVars.frameHeight - 8 - 64, 200, 8))
                    && ballYdir > 0) {
                ballYdir = -ballYdir;

                playSound("hit_paddle.wav");
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
                            breakBrick(i, j);
                            score += 5;
                            level.removeBrick();

                            playSound("hit.wav");

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

                playSound("hit_wall.wav");
            }

            // check ball collision with border top
            if (ballposY < 0) {
                ballYdir = -ballYdir;

                playSound("hit_wall.wav");
            }

            // check ball collision with border right
            if (ballposX > GlobalVars.frameWidth - 3 - 20) {
                ballXdir = -ballXdir;

                playSound("hit_wall.wav");
            }
        }

        repaint();
    }
}

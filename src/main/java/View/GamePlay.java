package View;

import Main.GameplayEvents;
import Main.GlobalVars;
import Model.GameTimeThread;
import Model.Level;
import Model.User;

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
import java.util.concurrent.Semaphore;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    // useful variables
    private boolean play;
    private boolean end;

    private long score;
    private long time;
    private int delay;
    private String timeStr;

    // some final variables
    final private Level level;
    final private User user;
    final private Timer timer;
    final private GameTimeThread gameTimeThread;
    final private Semaphore semaphore;

    // custom events (used to show the endgame menu from the controller)
    private GameplayEvents gameplayEvents;

    // paddle
    private int paddle = GlobalVars.frameWidth / 2 - 100;

    // position of ball
    private int ballposX;
    private int ballposY;
    private int ballXdir = -1;
    private double ballYdir = -2;

    /* -------------------------------------------------------------------------------------------------------- */
     // Game logic form here!
    /* -------------------------------------------------------------------------------------------------------- */

    public GamePlay(Level level, User user) {
        this.play = false;
        this.end = false;
        this.score = 0;
        this.time = 0;
        this.level = level;
        this.user = user;
        this.delay = (int) level.getBallSpeed(); // <- it modifies the ball speed (aka the update time of each frame)

        this.semaphore = new Semaphore(1);
        this.gameTimeThread = new GameTimeThread(this, semaphore);

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

    /* -------------------------------------------------------------------------------------------------------- */
    // The main graphics method (draw everything in the screen)
    /* -------------------------------------------------------------------------------------------------------- */
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
        if(!play && time == 0) {
            g.setColor(GlobalVars.textColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Premi SPACE per iniziare a giocare!", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi SPACE per iniziare a giocare!") / 2, GlobalVars.frameHeight / 2);

            // the time (fixed)
            g.setColor(GlobalVars.timerColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("00:00", 32, 32);
        }

        // the time (dynamic) ### NOT WORKING CORRECTLY ###
        if(play){
            timeStr = GlobalVars.timeParser(time);

            g.setColor(GlobalVars.timerColor);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(timeStr, 32, 32);
        }

        // when you won the game
        if (level.getBreakBricks() <= 0) {
            play = false;
            end = true;

            gameplayEvents.endMenuOpen(true);
            user.setTotPlayGame(user.getTotPlayGame() + 1);
            user.setTotBricksBreak(user.getTotBricksBreak() + score/5);
            user.setLevel(level.getNext_uuid());
        }

        // when you lose the game
        if (ballposY > GlobalVars.frameHeight) {
            play = false;
            end = true;
            gameplayEvents.endMenuOpen(false);

            user.setTotPlayGame(user.getTotPlayGame() + 1);
            user.setTotBricksBreak(user.getTotBricksBreak() + score/5);
        }

        // inGame menu
        if(!play && !end && time != 0) {
            showInGameMenu(g);
        }

        g.dispose();
    }

    public String getTimeStr() {
        return timeStr;
    }

    public long getTime() {
        return time;
    }

    public void addTime() {
        time++;
    }

    /* -------------------------------------------------------------------------------------------------------- */
    // Gameplay listener
    /* -------------------------------------------------------------------------------------------------------- */

    public void addGameplayListener(GameplayEvents gameplayEvents) {
        this.gameplayEvents = gameplayEvents;
    }

    /* -------------------------------------------------------------------------------------------------------- */
     // Private methods
    /* -------------------------------------------------------------------------------------------------------- */

    // Move paddle to left
    private void moveLeft() {
        play = true;
        paddle -= level.getPaddleSpeed();
    }

    // Move paddle to right
    private void moveRight() {
        play = true;
        paddle += level.getPaddleSpeed();
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
        if(!user.getSounds()) {
            return;
        }

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
                    if(level.getMap()[i][j] == 1) {
                        g.setColor(GlobalVars.brickColor);
                    } else if(level.getMap()[i][j] == 2) {
                        g.setColor(GlobalVars.extraBrickColor);
                    }

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
        g.setColor(GlobalVars.backgroundColorTransparent);
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

        // the text #3
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 18));
        g.drawString("Premi ENTER per chiudere la partita", GlobalVars.frameWidth / 2 - g.getFontMetrics().stringWidth("Premi ENTER per chiudere la partita")/2, 352);
    }

    /* -------------------------------------------------------------------------------------------------------- */
     // Override methods for keyListener and Timer
    /* -------------------------------------------------------------------------------------------------------- */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Listen to pressed keys
    @Override
    public void keyPressed(KeyEvent e) {
        // to start the game
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !play && time == 0) {
            play = true;
            if(gameTimeThread.isAlive()) {
                semaphore.release();
            } else {
                gameTimeThread.start();
            }

            return;
        }

        // move right paddle
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && play) {
            if (paddle >= GlobalVars.frameWidth - 200) {
                paddle = GlobalVars.frameWidth - 210;
            } else {
                moveRight();
            }

            return;
        }

        // move left paddle
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && play) {
            if (paddle < 10) {
                paddle = 10;
            } else {
                moveLeft();
            }

            return;
        }

        // In game menu open
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && play && !end && time != 0) {
            play = false;
            try {
                semaphore.acquire();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return;
        }

        // In game menu close
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !play && !end && time != 0) {
            play = true;
            try {
                semaphore.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return;
        }

        // In game menu close & main menu
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play && !end && time != 0) {
            gameplayEvents.inGameMenuClose();

            return;
        }

        // In game menu close & restart
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !play && !end && time != 0) {
            this.play = false;
            this.end = false;
            this.score = 0;
            this.time = 0;
            this.paddle = GlobalVars.frameWidth / 2 - 100;
            this.delay = (int) level.getBallSpeed(); // <- it modifies the ball speed (aka the update time of each frame)

            level.reloadMap();
            generateBallPosition();
        }
    }

    /* -------------------------------------------------------------------------------------------------------- */

    // Run automatically thanks to the Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (!play) {
            repaint();
            return;
        }

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
                        if(level.getMap()[i][j] == 1) {
                            score += 5;
                        } else if(level.getMap()[i][j] == 2) {
                            score += 10;
                        }

                        level.removeBrick(i, j);
                        breakBrick(i, j);
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


        repaint();
    }
}

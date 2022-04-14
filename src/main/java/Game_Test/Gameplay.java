package Game_Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.Timer;

import Main.Main;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements ActionListener, KeyListener {
    // usefull global variables
    private boolean play = false;
    private int score = 0;
    private int col = 10;
    private int row = 3;
    private int totalbricks = col * row;

    // Timer
    private Timer timer;
    private int delay = 8; // <- it modifies the ball speed (aka the update time of each frame)

    // paddle
    private int playerX = 1920 / 2 - 200;

    // position of ball
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay() throws IOException, ParseException {
        map = new MapGenerator(row, col);

        generateBallPosition();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 1920, 1080);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.RED);
        g.fillRect(0, 0, 3, 1080);
        g.fillRect(0, 0, 1920, 3);
        g.fillRect(1917, 0, 3, 1080);

        // the scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 1920 / 2, 30);

        // the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 1050, 400, 8);

        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);

        // when you won the game
        if (totalbricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 1920 / 2, 300);

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 1920 / 2, 350);
        }

        // when you lose the game
        if (ballposY > 1080) {
            play = false;

            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: " + score, 190, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);

            timer.stop();
        }

        g.dispose();
    }

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

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && play) {
            if (playerX >= 1920 - 400) {
                playerX = 1920 - 410;
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

        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
            timer.start();

            play = true;

            ballXdir = -1;
            ballYdir = -2;

            playerX = 1920 / 2 - 200;

            score = 0;
            totalbricks = row * col;

            generateBallPosition();

            try {
                map = new MapGenerator(row, col);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            repaint();
        }
    }

    // This method is called automatically through the Timer (MAIN GAME LOGIC HERE)
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (!play) {
            return;
        }
        // check ball collision with the paddle
        if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 1050, 400, 8)) && ballYdir > 0) {
            ballYdir = -ballYdir;

            playSound("hit_paddle.wav");
        }

        // check map collision with the ball
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if (map.map[i][j] != 0) {
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;

                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                    if (ballRect.intersects(brickRect)) {
                        map.setBrickValue(0, i, j);
                        score += 5;
                        totalbricks--;

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
        if (ballposX > 1910) {
            ballXdir = -ballXdir;

            playSound("hit_wall.wav");
        }

        repaint();
    }

    private void moveLeft() {
        play = true;
        playerX -= 15;
    }

    private void moveRight() {
        play = true;
        playerX += 15;
    }

    private void generateBallPosition() {
        ballposX = (int) (Math.random() * 1920);
        ballposY = (int) (Math.random() * 500) + 500;
    }

    private void playSound(String soundPath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getClassLoader().getResourceAsStream(soundPath));
            clip.open(inputStream);
            clip.start();
        } catch (Exception xe) {
            System.err.println(xe.getMessage());
        }
    }
}

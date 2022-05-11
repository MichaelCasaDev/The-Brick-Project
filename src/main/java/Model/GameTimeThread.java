package Model;

import View.GamePlay;

import javax.swing.*;
import java.util.concurrent.Semaphore;

/**
 * Thread for the game time
 */
public class GameTimeThread extends Thread {
    private final GamePlay gamePlay;
    private final Semaphore semaphore;

    /**
     * Constructor
     * @param gamePlay the gameplay object
     * @param semaphore the semaphore of the thread
     */
    public GameTimeThread(GamePlay gamePlay, Semaphore semaphore) {
        this.gamePlay = gamePlay;
        this.semaphore = semaphore;
    }

    /**
     * Main run method
     */
    public void run() {
        while(true) {
            try {
                semaphore.acquire();
                gamePlay.addTime();
                semaphore.release();

                sleep(1000);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}

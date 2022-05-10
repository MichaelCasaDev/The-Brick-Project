package Model;

import View.GamePlay;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class GameTimeThread extends Thread {
    private final GamePlay gamePlay;
    private final Semaphore semaphore;

    public GameTimeThread(GamePlay gamePlay, Semaphore semaphore) {
        this.gamePlay = gamePlay;
        this.semaphore = semaphore;
    }

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

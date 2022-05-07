package Model;

import View.GamePlay;

import java.util.concurrent.Semaphore;

public class GameTimeThread extends Thread {
    private GamePlay gamePlay;
    private Semaphore semaphore;

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
                e.printStackTrace();
            }
        }
    }
}

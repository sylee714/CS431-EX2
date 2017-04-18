/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtest;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author MingKie
 */
public class processingThread implements Runnable {
    
    private BlockingQueue<String> fileContentQueue;
    private int[] count;
    
    public processingThread(BlockingQueue fileContentQueue, int[] count) {
        this.fileContentQueue = fileContentQueue;
        this.count = count; // 0-9 (index 0-9), A-Z (index 10-35), and a-z (index 36-61)
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                int ascii = 0;
                String content = fileContentQueue.take();
                for (int i = 0; i < content.length(); ++i) {
                    ascii = (int) content.charAt(i);
                    if (ascii >= 48 && ascii <= 57) {
                        ascii = ascii - 48;
                        count[ascii] = count[ascii] + 1;
                    } else if (ascii >= 65 && ascii <= 90) {
                        ascii = ascii - 55;
                        count[ascii] = count[ascii] + 1;
                    } else if (ascii >= 97 && ascii <= 122) {
                        ascii = ascii - 61;
                        count[ascii] = count[ascii] + 1;
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
}

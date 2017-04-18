/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author MingKie
 */
public class fileReadThread implements Runnable {

    private BlockingQueue<String> fileQueue;
    private BlockingQueue<String> fileContentQueue;
    
    public fileReadThread(BlockingQueue fileQueue, BlockingQueue fileContentQueue) {
        this.fileQueue = fileQueue;
        this.fileContentQueue = fileContentQueue;
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String fileName = fileQueue.take();
                String content = "";
                File file = new File(fileName);
                try (Scanner sc = new Scanner(file)) {
                    while(sc.hasNextLine()) {
                        content = sc.nextLine() + content;
                    }
                    fileContentQueue.put(content);
                }      
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (FileNotFoundException ex) {
            }
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadtest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author MingKie
 */
public class ThreadTest {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        BlockingQueue fileQueue= new LinkedBlockingQueue();
        BlockingQueue fileContentQueue= new LinkedBlockingQueue();
        int[] count = new int[62]; // 0-9 (index 0-9), A-Z (index 10-35), and a-z (index 36-61)
        for (int i = 0; i < count.length; ++i) {
            count[i] = 0;
        }
        Thread fileRead = new Thread(new fileReadThread(fileQueue, fileContentQueue));
        Thread processing = new Thread(new processingThread(fileContentQueue, count));
        Thread io = new Thread(new userIOThread(fileQueue, fileContentQueue, fileRead, processing, count)); 
        fileRead.start();
        processing.start();
        io.start(); 
        
    }
    
}

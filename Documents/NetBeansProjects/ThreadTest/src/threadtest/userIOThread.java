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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MingKie
 */
public class userIOThread implements Runnable{
   
    private BlockingQueue<String> fileQueue;
    private BlockingQueue<String> fileContentQueue;
    private int[] count;
    private Scanner sc;
    private String command;
    private String fileName;
    private Thread fileRead;
    private Thread processing;
    
    public userIOThread(BlockingQueue fileQueue, BlockingQueue fileContentQueue, Thread fileRead, Thread processing, int[] count) {
        this.fileQueue = fileQueue;
        this.fileContentQueue = fileContentQueue;
        this.fileRead = fileRead;
        this.processing = processing;
        sc = new Scanner(System.in);
        this.count = count;
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println("Enter a command: ");
            command = sc.next();
            switch(command) {
                case "exit":
                    if (sc.nextLine().isEmpty()) {         
                        fileRead.interrupt();
                        processing.interrupt();
                        Thread.currentThread().interrupt();   
                        return;
                    } else {
                        System.out.println("Wrong command.");
                    }
                    
                    break;
                case "counts":
                    if (sc.nextLine().isEmpty()) {
                        print();
                        
                    } else {
                        System.out.println("Wrong command.");
                    }
                    break;
                case "read":
                    fileName = sc.nextLine();
                    fileName = fileName.replaceAll("\\s+","");
                    if (fileExit(fileName)) {
                    try {
                        fileQueue.put(fileName);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(userIOThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    break;
                default:
                    System.out.println("Wrong command.");
            }
        }
    }
    
    public boolean fileExit(String fileName) {
        boolean found = false;
        try {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        sc.close();
        found = true;
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return found;
    }
    
    public void print() {
        System.out.println("0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 10; ++i) {
            System.out.print(count[i] + " ");
        }
        System.out.println();
        System.out.println("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        for (int i = 10; i < 36; ++i) {
            System.out.print(count[i] + " ");
        }
        System.out.println();
        System.out.println("a b c d e f g h i j k l m n o p q r s t u v w x y z");
        for (int i = 36; i < 62; ++i) {
            System.out.print(count[i] + " ");
        }
        System.out.println();
    }
    
}

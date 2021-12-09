package ru.job4j.threads.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Speed - Mb/second
 */
public class Wget implements Runnable {

    private String url;

    private int speed;

    private final String[] args;

    public Wget(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     url.contains("/") ? url.split("/")[url.split("/").length - 1] : url)
        ) {

            byte[] dataBuffer = new byte[speed];
            int bytesRead = 0;
            int sumBytesRead = 0;
            long before = System.currentTimeMillis();
            while (bytesRead != -1) {
                bytesRead = in.read(dataBuffer, 0, speed);
                sumBytesRead = sumBytesRead + bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead != -1 ? bytesRead : 0);
            }
            long after = System.currentTimeMillis();
            long timeForLoad = after - before;
            long difference = ((sumBytesRead / this.speed) - timeForLoad / 1000) * 1000;
            if (difference > 0) {
                System.out.println("Waiting " + difference + " milliseconds");
                Thread.sleep(difference);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Wget(args).wgetStarter();
    }

    private void wgetStarter() throws InterruptedException {
        try {
            argsValidator();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        this.url = this.args[0];
        this.speed = Integer.parseInt(this.args[1]);
        Thread wgetThread = new Thread(this);
        wgetThread.start();
        wgetThread.join();
    }

    private void argsValidator() throws IllegalArgumentException {
        if (args.length > 2) {
            throw new IllegalArgumentException("Incorrect size of input args!");
        }
    }
}

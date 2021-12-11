package ru.job4j.threads.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

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
                     Paths.get(new URI(url).getPath()).getFileName().toString())
        ) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            int bytesWritten = 0;
            long before;
            long after;
            long differenceTime;
            while (bytesRead != -1) {
                before = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                fileOutputStream.write(dataBuffer, 0, bytesRead != -1 ? bytesRead : 0);
                bytesWritten = bytesWritten + bytesRead;
                after = System.currentTimeMillis();
                if (bytesWritten >= speed) {
                    bytesWritten = 0;
                    differenceTime = after - before;
                    if (differenceTime < 1000) {
                        long sleepTime = 1000 - differenceTime;
                        System.out.println("Waiting " + sleepTime + " milliseconds");
                        Thread.sleep(sleepTime);
                    }
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
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

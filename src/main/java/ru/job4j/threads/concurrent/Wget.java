package ru.job4j.threads.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;

    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            while (bytesRead != -1) {
                long before = System.nanoTime();
                bytesRead = in.read(dataBuffer, 0, 1024);
                fileOutputStream.write(dataBuffer, 0, bytesRead != -1 ? bytesRead : 0);
                long after = System.nanoTime();
                long delta = after - before;
                if (delta < this.speed) {
                    System.out.println("Waiting " + (this.speed - delta) + " nanoseconds");
                    Thread.sleep((this.speed - delta) / 1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}

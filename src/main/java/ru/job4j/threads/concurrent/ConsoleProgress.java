package ru.job4j.threads.concurrent;

import java.util.HashMap;
import java.util.Random;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        Random random = new Random();
        HashMap<Integer, String> loadSymbols = new HashMap<>();
        loadSymbols.put(0, "/");
        loadSymbols.put(1, "\\");
        loadSymbols.put(2, "|");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                System.out.print("\rLoading : " + loadSymbols.get(random.nextInt(3)));
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}

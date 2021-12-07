package ru.job4j.threads.concurrent;

public class Wget {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 100; i++) {
            Thread.sleep(1000);
            System.out.print("\rLoading : " + i  + "%");
        }
    }
}

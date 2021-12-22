package ru.job4j.threads.synchronize.parsefile;

import java.io.*;
import java.util.function.Predicate;

public final class FileParser {

    private final File file;

    public FileParser(File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        return transformFileToString(data -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return transformFileToString(d -> d < 0x80);
    }

    private String transformFileToString(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(file)) {
            int data;
            while ((data = inputStream.read()) > 0) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
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
        InputStream i = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = i.read()) > 0) {
            if (filter.test(data)) {
                output.append((char) data);
            }
        }
        return output.toString();
    }
}
package ru.job4j.threads.synchronize.parsefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FileWriter {

    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}

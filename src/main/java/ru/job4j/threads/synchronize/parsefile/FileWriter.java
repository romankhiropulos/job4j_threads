package ru.job4j.threads.synchronize.parsefile;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@ThreadSafe
public final class FileWriter {

    @GuardedBy("this")
    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(this.file)) {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}

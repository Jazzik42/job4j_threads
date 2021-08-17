package ru.job4j.io;

import java.io.*;

public class WriteFile {
    public synchronized void saveContent(String content, File file) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        }
    }
}

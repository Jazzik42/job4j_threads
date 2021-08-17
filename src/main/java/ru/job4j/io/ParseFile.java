package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuffer output = new StringBuffer();
        try (BufferedReader io = new BufferedReader(new FileReader((file)))) {
            int data;
            while ((data = io.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
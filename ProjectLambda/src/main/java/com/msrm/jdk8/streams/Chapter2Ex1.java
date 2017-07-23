package com.msrm.jdk8.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Chapter2Ex1 {

    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("alice.txt")),
                StandardCharsets.UTF_8); // Read file into string
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        // Count all the long words in the list
        int count = 0;
        for (String w : words) {
            if (w.length() > 12) count++;
        }

        System.out.println("Count : " + count);

    }

}

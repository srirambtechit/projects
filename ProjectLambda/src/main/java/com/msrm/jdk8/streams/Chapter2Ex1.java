package com.msrm.jdk8.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Chapter2Ex1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        String contents = new String(Files.readAllBytes(Paths.get("alice.txt")),
                StandardCharsets.UTF_8); // Read file into string
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        // Run parallel code
        int processors = Runtime.getRuntime().availableProcessors();
        int incr = words.size() / processors;
        int len = words.size();

        System.out.println("Size : " + words.size());
        System.out.println("Processors : " + processors);

        WordCounter[] r = new WordCounter[processors];
        for (int i = 0; i < processors; i++) {
            int end = incr * (i + 1) - 1;
            r[i] = new WordCounter(i * incr, len - end < incr ? len - 1 : end, words);
            new Thread(r[i]).start();
        }

        // Explicitly blocking IO for Main thread
        while (true) {
            if (running == processors) break;
        }

        int summer = 0;
        for (int i = 0; i < processors; i++) {
            summer += r[i].count();
            System.out.println("Main --> Count is " + summer);
        }

        System.out.println("Total words are " + summer);
        System.out.println(Arrays.toString(r));
    }

    private static int running = 0;

    private static class WordCounter implements Runnable {

        private int start;
        private int end;
        private List<String> words;
        private Long count = 0L;

        public WordCounter(int start, int end, List<String> words) {
            this.start = start;
            this.end = end;
            this.words = words;
        }

        public long count() {
            return count;
        }

        @Override
        public void run() {
            long c = 0;
            System.out.printf("WordCounter --> Processing from %d till %d%n", start, end);
            for (int i = start; i <= end; i++) {
                if (words.get(i).length() > 12) c++;
            }
            System.out.printf("WordCounter --> %s's count is %d%n",
                    Thread.currentThread().getName(), c);
            count = c;
            synchronized (this) {
                running++;
            }
        }

        @Override
        public String toString() {
            return "WordCounter [count=" + count + "]";
        }

    }
}

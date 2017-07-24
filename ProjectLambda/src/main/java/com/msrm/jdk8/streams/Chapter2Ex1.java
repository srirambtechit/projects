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

        int summer = 0;
        for (int i = 0; i < processors; i++) {
            synchronized (r[i]) {
                try {
                    System.out.println("Main --> is awaiting for data");
                    r[i].wait();
                    summer += r[i].count();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Main --> Count is " + summer);
            }
        }
    }

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
            synchronized (this) {
                count = c;
                notifyAll();
            }
        }

    }

}

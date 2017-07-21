package com.msrm.jdk8.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class RealTimeUsage1 {

    public static void main(String[] args) {
        try (Stream<String> lines = Files.lines(Paths.get("sharedata.txt"))) {
            //@formatter:off
            long count = lines.filter(line -> !line.isEmpty())
                .map(line -> line.split(" "))
                .flatMap(Arrays::stream)
                .count();
            System.out.println("Count : " + count);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

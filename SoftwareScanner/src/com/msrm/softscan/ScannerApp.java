package com.msrm.softscan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ScannerApp {

    private String inputFile;
    private String outputFile;
    private String searchString;

    public ScannerApp() {
        // default value
        this.searchString = "name=";
    }

    public static void main(String[] args) {
        ScannerApp app = new ScannerApp();
        app.checkCmdArgs(args);
        app.processFile();
    }

    public void processFile() {
        System.out.print("Processing");
        try {
            List<String> hostsResult = new ArrayList<>();
            List<String> hosts = Files.readAllLines(Paths.get(inputFile));
            hosts.forEach(host -> {
                StringBuilder response = new StringBuilder();
                try {
                    InetAddress ip = InetAddress.getByName(host);
                    // Timeout period is 5 seconds
                    if (!ip.isReachable(5 * 1000)) {
                        response.append("PING NOT FOUND");
                    } else {
                        if (Files.exists(Paths.get(URI.create("file:///tmp/scanner")))) {
                            Files.readAllLines(
                                    Paths.get(URI.create("file:///tmp/scanner/data.txt")))
                                    .forEach(line ->
                            {
                                        if (line.contains(searchString)) {
                                            response.append(line.substring(searchString.length()
                                                    + searchString.indexOf(0) + 1));
                                        }
                                    });
                        } else {
                            response.append("SOFTWARE NOT FOUND");
                        }
                    }
                } catch (UnknownHostException e) {
                    response.append("PING NOT FOUND");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hostsResult.add(String.format("%-15s %-15s", host, response));
                System.out.print("..");
            });

            Files.write(Paths.get(outputFile), hostsResult, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);

            System.out.println("\n");
            hostsResult.forEach(System.out::println);
            System.out.println("\nProcessing completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkCmdArgs(String[] args) {
        if (args.length == 0) return;
        if (args.length != 0 && args.length % 2 != 0) {
            printUsage();
            System.exit(1);
        }
        int len = args.length;
        while (len > 0) {
            int value = --len;
            int option = --len;
            if ("--file".equals(args[option])) inputFile = args[value];
            if ("--output".equals(args[option])) outputFile = args[value];
            if ("--search".equals(args[option])) searchString = args[value];
        }
    }

    private void printUsage() {
        System.out.println("Usage: \tScannerApp --file C:\\Temp\\input.txt\n"
                + "\tScannerApp --file C:\\Temp\\input.txt " + "--output C:\\Temp\\output.txt "
                + "--search searchToken");
    }

}

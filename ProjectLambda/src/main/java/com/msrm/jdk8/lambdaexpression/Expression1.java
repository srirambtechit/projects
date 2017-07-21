package com.msrm.jdk8.lambdaexpression;

public class Expression1 {

    public static void main(String[] args) {
        // Traditional way of writing
        // Anonymous class code
        // Too verbose, makes less readability
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, World");
            }
        }).start();

        // a line of code in Lambda expression
        new Thread(() -> System.out.println("Hello, World")).start();
    }

}

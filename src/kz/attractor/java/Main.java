package kz.attractor.java;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson46Server("localhost", 6574).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

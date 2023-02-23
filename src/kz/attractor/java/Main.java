package kz.attractor.java;

import kz.attractor.java.HW44.Lesson45Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson45Server("localhost", 6574).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

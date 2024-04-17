package org.example;

import java.util.Scanner;
import org.example.pizzeria.Pizzeria;

public class Main {
    public static void main(String[] args) {

        Pizzeria pizzeria = new Pizzeria();

        pizzeria.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "start" -> pizzeria.start();
                case "stop" -> pizzeria.stop();
                case "end" -> {
                    pizzeria.stop();
                    System.out.println("Ends");
                    return;
                }
            }
        }

    }



}
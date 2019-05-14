package com.kpi.course.work.view;

import java.util.Scanner;

public class View {

    private Scanner scanner;

    public View() {
        scanner = new Scanner(System.in);
    }

    public void printText() {
        System.out.println();
    }

    public void printText(String text) {
        System.out.println(text);
    }

    public void printError(String error) {
        System.err.println(error);
    }

    public void printString(String str) {
        System.out.print(str);
    }

    public String readDataFromConsole() {
        return scanner.next();
    }
}

package com.hartmanmark.schooldb.service;

import java.util.Scanner;

public class ConsoleReader {

    public String read() {
        return new Scanner(System.in).nextLine();
    }
}

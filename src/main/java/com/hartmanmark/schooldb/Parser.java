package com.hartmanmark.schooldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Parser {

    private List<String> lines = new ArrayList<String>();

    public String getRundomLine(File path) throws IOException {
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }
}

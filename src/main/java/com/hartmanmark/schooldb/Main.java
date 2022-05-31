package com.hartmanmark.schooldb;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Parser parser = new Parser();
        Connector connector = new Connector();
        Reader reader = new Reader();
        Printer printer = new Printer(reader, connector, parser);
        printer.print();
    }
}

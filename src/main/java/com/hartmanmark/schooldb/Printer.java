package com.hartmanmark.schooldb;

import java.io.IOException;

public class Printer {

    private Reader reader;
    private Connector connector;
    private Parser parser;

    public Printer(Reader reader, Connector connector, Parser parser) {
        this.reader = reader;
        this.connector = connector;
        this.parser = parser;
    }

    public void print() throws IOException {
        reader.read();
        for (int i = 0; i < 200; i++) {
            System.out.println(connector.connect(parser.getRundomLine(reader.getPathToFirstName()),
                    parser.getRundomLine(reader.getPathToLastName())));
        }
    }
}

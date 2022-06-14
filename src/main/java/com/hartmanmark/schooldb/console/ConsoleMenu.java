package com.hartmanmark.schooldb.console;

import java.util.Scanner;

import com.hartmanmark.schooldb.exception.InputIsNonChooseRangeException;
import com.hartmanmark.schooldb.exception.InputIsNonIntegerException;
import com.hartmanmark.schooldb.service.Validator;

public class ConsoleMenu {
	private String input;

	public void readChoose() throws InputIsNonIntegerException, InputIsNonChooseRangeException {
		System.err.println(String.format("%100s", "").replace(' ', '-') + "\n"); //for me. in final version will remove
		System.out.print("Welcom to simple sql-jdbc-school app.");
		String[] options = { "",
				"1 - Find all groups with less or equals student count",
				"2 - Find all students related to course with given name",
				"3 - Add new student",
				"4 - Delete student by STUDENT_ID",
				"5 - Add a student to the course (from a list)",
				"6 - Remove the student from one of his or her courses",
				"7 - Exit", "" };
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				printMenu(options);
				Validator.verify(input = scanner.nextLine());
				if (input.equals("1")) {
					System.err.println("method №1");//for me. in final version will remove
				} else if (input.equals("2")) {
					System.err.println("method №2");//for me. in final version will remove
				} else if (input.equals("3")) {
					System.err.println("method №3");//for me. in final version will remove
				} else if (input.equals("4")) {
					System.err.println("method №4");//for me. in final version will remove
				} else if (input.equals("5")) {
					System.err.println("method №5");//for me. in final version will remove
				} else if (input.equals("6")) {
					System.err.println("method №6");//for me. in final version will remove
				} else if (input.equals("7")) {
					System.err.println("Exit");//for me. in final version will remove
					break;
				}
			}
		}
	}

	public void printMenu(String[] options) {
		for (String option : options) {
			System.out.println(option);
		}
		System.out.print("Choose your option : ");
	}
}

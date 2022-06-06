package com.hartmanmark.schooldb;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {

	private String groupName;
	private String firstName;
	private String lastName;
	protected List<String> randomFirstName = new ArrayList<String>();
	protected List<String> randomLastName = new ArrayList<String>();
	private Inserter inserter = new Inserter();

	public void generate(File pathToFirstName, File pathToLastName) throws IOException, SQLException {
		getListOfRandomFirstNames(pathToFirstName);
		getListOfRandomLastNames(pathToLastName);
		getStudentsNames();
		getGroupsNames();
		inserter.insertCourses();
	}

	private String getRandomAlphaString() {
		String alphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder randomAlphaString = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			int index = (int) (alphaString.length() * Math.random());
			randomAlphaString.append(alphaString.charAt(index));
		}
		return randomAlphaString.toString();
	}

	private String getRandomNumericString() {
		String AlphaString = "1234567890";
		StringBuilder randomNumericString = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			int index = (int) (AlphaString.length() * Math.random());
			randomNumericString.append(AlphaString.charAt(index));
		}
		return randomNumericString.toString();
	}

	private void getGroupsNames() throws SQLException {
		for (int i = 1; i < 11; i++) {
			String firstParthOfGroupName = getRandomAlphaString();
			String lastParthOfGroupName = getRandomNumericString();
			groupName = firstParthOfGroupName + '-' + lastParthOfGroupName;
			inserter.insertGroups(groupName, i);
		}
		System.err.println("Groups added");/////////////////////////
	}

	private void getListOfRandomFirstNames(File pathToFirstNames) throws IOException {
		Scanner scannerFirstName = new Scanner(pathToFirstNames);
		while (scannerFirstName.hasNextLine()) {
			randomFirstName.add(scannerFirstName.nextLine());
		}
		scannerFirstName.close();
	}

	private void getStudentsNames() throws IOException, SQLException {
		Random random = new Random();
		for (int i = 1; i < 201; i++) {
			firstName = randomFirstName.get(random.nextInt(randomFirstName.size()));
			lastName = randomLastName.get(random.nextInt(randomLastName.size()));
			inserter.insertStudents(firstName, lastName, i);
		}
		System.err.println("Students added");///////////////////////////
	}

	private void getListOfRandomLastNames(File pathToLastNames) throws IOException {
		Scanner scannerLastName = new Scanner(pathToLastNames);
		while (scannerLastName.hasNextLine()) {
			randomLastName.add(scannerLastName.nextLine());
		}
		scannerLastName.close();
	}
}

package com.hartmanmark.schooldb;

//import java.util.ArrayList;
//import java.util.List;

public class Generator {

//    private List<String> listOfGroupNames = new ArrayList<String>();
    String groupName;

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

    public void getRandomGroupNames() {
        System.out.println();
        for (int i = 1; i < 11; i++) {
            String firstParthOfGroupName = getRandomAlphaString();
            String lastParthOfGroupName = getRandomNumericString();
            groupName = firstParthOfGroupName + '-' + lastParthOfGroupName;
//            listOfGroupNames.add(groupName);
            System.out.println(groupName);
        }
//        System.out.println(listOfGroupNames);
    }
}

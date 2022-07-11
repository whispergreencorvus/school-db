package com.hartmanmark.schooldb.model;

public class Group {

    public Group(String groupName, String namberOfStudents) {
        this.groupName = groupName;
        this.namberOfStudents = namberOfStudents;
    }

    private String groupName;
    private String namberOfStudents;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamberOfStudents() {
        return namberOfStudents;
    }

    public void setNamberOfStudents(String namberOfStudents) {
        this.namberOfStudents = namberOfStudents;
    }

}

package ru.makar.icss.lab3.model;

import java.util.ArrayList;
import java.util.List;

public class GroupsInfo {
    private List<Group> groups;
    private List<Student> students;

    public GroupsInfo() {
        groups = new ArrayList<>();
        students = new ArrayList<>();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Student> getStudents() {
        return students;
    }
}

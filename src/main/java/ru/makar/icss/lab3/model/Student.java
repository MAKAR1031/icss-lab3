package ru.makar.icss.lab3.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Student {
    private String lastName;
    private String firstName;
    private String patronymic;
    private LocalDate birthDate;
    private int age;
    private boolean onBudget;
    private int groupRef;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.age = (int) LocalDate.now().until(birthDate, ChronoUnit.YEARS);
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        birthDate = LocalDate.now().minus(age, ChronoUnit.YEARS);
        this.age = age;
    }

    public boolean isOnBudget() {
        return onBudget;
    }

    public void setOnBudget(boolean onBudget) {
        this.onBudget = onBudget;
    }

    public int getGroupRef() {
        return groupRef;
    }

    public void setGroupRef(int groupRef) {
        this.groupRef = groupRef;
    }
}

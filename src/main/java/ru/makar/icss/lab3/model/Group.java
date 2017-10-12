package ru.makar.icss.lab3.model;

import java.math.BigDecimal;

public class Group {
    private int id;
    private String name;
    private String department;
    private BigDecimal baseCost;
    private BigDecimal educationCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public BigDecimal getEducationCost() {
        return educationCost;
    }

    public void setEducationCost(BigDecimal educationCost) {
        this.educationCost = educationCost;
    }
}

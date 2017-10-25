package ru.makar.icss.lab3.parser.impl;

import ru.makar.icss.lab3.model.GroupsInfo;

import java.math.BigDecimal;

public class DataProcessor {
    public void process(GroupsInfo info) {
        if (info == null) {
            return;
        }
        info.getGroups().forEach(g -> {
            BigDecimal baseCost = g.getBaseCost();
            long studentsCount = info.getStudents().stream()
                    .filter(s -> s.getGroupRef() == g.getId() && s.isOnBudget())
                    .count();
            g.setEducationCost(baseCost.multiply(BigDecimal.valueOf(studentsCount < 2 ? 2 : studentsCount)));
        });
    }
}

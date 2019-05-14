package com.kpi.course.work.model.validators;

import com.kpi.course.work.model.figures.Parallelepiped;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputDataValidator {

    public Set<String> validate(List<Parallelepiped> inputData) {
        Set<String> incorrect = new HashSet<>();

        for (Parallelepiped p : inputData) {
            if (p.getHeight() < 1 || p.getBase().getLength() < 1 || p.getBase().getWidth() < 1) {
                incorrect.add(p.getName());
            }
        }

        return incorrect;
    }
}

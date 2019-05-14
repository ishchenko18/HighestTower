package com.kpi.course.work.generator;

import com.kpi.course.work.model.figures.Base;
import com.kpi.course.work.model.figures.Parallelepiped;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private String[] aplphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public List<Parallelepiped> generateData() {
        int count = generateInt(26, 1);

        List<Parallelepiped> data = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Parallelepiped parallelepiped = new Parallelepiped(generateInt(100, 1), 1, aplphabet[i],
                    new Base(generateInt(100, 1), generateInt(100, 1)));

            data.add(parallelepiped);
        }

        return data;
    }

    private int generateInt(int maxValue, int minValue) {
        return (int) (Math.random() * maxValue) + minValue;
    }
}

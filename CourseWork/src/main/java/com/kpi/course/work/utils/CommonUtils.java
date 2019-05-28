package com.kpi.course.work.utils;

import com.kpi.course.work.model.algorithms.Algorithm;
import com.kpi.course.work.model.figures.Base;
import com.kpi.course.work.model.figures.Parallelepiped;
import com.kpi.course.work.generator.DataGenerator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class CommonUtils {

    private Properties properties;
    private DataGenerator dataGenerator;

    public CommonUtils() throws Exception {
        dataGenerator = new DataGenerator();
        properties = new Properties();
        properties.load(Algorithm.class.getClassLoader().getResourceAsStream("application.properties"));
    }

    public List<Parallelepiped> dataSetup(int algorithm, int variant) throws Exception {
        return (variant == 1
                ? readFile(properties.getProperty(algorithm == 1 ? "algorithm.dynamical" : "algorithm.greedy"))
                : dataGenerator.generateData()).stream()
                .flatMap(p -> buildAllVariety(p).stream())
                .collect(Collectors.toList());
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private List<Parallelepiped> readFile(String fileName) throws Exception {

        List<Parallelepiped> readData = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));

        for (String line : lines) {
            String[] splittedLine = line.split("\\s");
            readData.add(new Parallelepiped(Integer.valueOf(splittedLine[3]), 1, splittedLine[0],
                    new Base(Integer.valueOf(splittedLine[1]), Integer.valueOf(splittedLine[2]))));
        }

        return readData;
    }


    private List<Parallelepiped> buildAllVariety(Parallelepiped p) {
        Parallelepiped p1 = new Parallelepiped(p.getBase().getLength(), p.getNumber() + 1, p.getName(),
                new Base(p.getHeight(), p.getBase().getWidth()));
        Parallelepiped p2 = new Parallelepiped(p.getBase().getWidth(), p1.getNumber() + 1, p.getName(),
                new Base(p.getHeight(), p.getBase().getLength()));

        return Arrays.asList(p, p1, p2);
    }
}

package com.kpi.course.work.controllers;

import com.kpi.course.work.model.algorithms.dynamical.DynamicalAlgorithm;
import com.kpi.course.work.model.algorithms.greedy.GreedyAlgorithm;
import com.kpi.course.work.model.exceptions.NotPositiveValueException;
import com.kpi.course.work.model.figures.Parallelepiped;
import com.kpi.course.work.model.validators.InputDataValidator;
import com.kpi.course.work.model.writer.ExcelWriter;
import com.kpi.course.work.utils.CommonUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationController {

    private DynamicalAlgorithm dynamicalAlgorithm;
    private GreedyAlgorithm greedyAlgorithm;
    private InputDataValidator validator;
    private CommonUtils utils;
    private ExcelWriter writer;

    public ApplicationController() throws Exception {
        dynamicalAlgorithm = new DynamicalAlgorithm();
        greedyAlgorithm = new GreedyAlgorithm();
        utils = new CommonUtils();
        validator = new InputDataValidator();
        writer = new ExcelWriter();
    }

    public Pair<List<Parallelepiped>, List<Parallelepiped>> solveTask(int algorithm, int dataPreparation) throws Exception {
        List<Parallelepiped> inputData = utils.dataSetup(algorithm, dataPreparation);
        Set<String> incorrect = validator.validate(inputData);

        if (!incorrect.isEmpty()) {
            throw new NotPositiveValueException(incorrect);
        }

        if (inputData.size() > 0) {
            Pair<List<Parallelepiped>, List<Parallelepiped>> result = algorithm == 1
                    ? Pair.of(inputData, dynamicalAlgorithm.solve(inputData))
                    : Pair.of(inputData, greedyAlgorithm.solve(inputData));

            List<Parallelepiped> uniqueParallelepipeds = result.getLeft().stream()
                    .filter(p -> p.getNumber() == 1)
                    .collect(Collectors.toList());

            writer.writeDataToExcel(uniqueParallelepipeds, result.getRight());

            return result;
        } else {
            throw new IllegalArgumentException("Input data is empty. Please, check input file.");
        }
    }
}

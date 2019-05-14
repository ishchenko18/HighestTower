package com.kpi.course.work.model.algorithms;

import com.kpi.course.work.model.figures.Parallelepiped;

import java.util.Comparator;
import java.util.List;

public class Algorithm {
    public static final int NO_WAY = 0;

    protected int[][] matrix;

    protected int[][] buildMatrix(List<Parallelepiped> parallelepipeds) {
        int[][] matrix = new int[parallelepipeds.size()][parallelepipeds.size()];

        for (int i = 0; i < parallelepipeds.size(); i++) {
            for (int j = 0; j < parallelepipeds.size(); j++) {
                Parallelepiped first = parallelepipeds.get(i);
                Parallelepiped second = parallelepipeds.get(j);

                if (first.isBaseCompatible(second.getBase()) && !first.getName().equals(second.getName()) && matrix[i][j] == 0) {
                    matrix[i][j] = second.getHeight();

                    if (first.getBase().equals(second.getBase())) {
                        matrix[j][i] = Algorithm.NO_WAY;
                    }
                } else {
                    matrix[i][j] = Algorithm.NO_WAY;
                }
            }
        }

        return matrix;
    }

    protected Parallelepiped findHighestParallelepiped(List<Parallelepiped> parallelepipeds) {
        parallelepipeds.sort(Comparator.comparing(Parallelepiped::getHeight));
        return parallelepipeds.get(parallelepipeds.size() - 1);
    }
}

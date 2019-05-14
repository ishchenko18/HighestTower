package com.kpi.course.work.model.algorithms.dynamical;

import com.kpi.course.work.model.algorithms.Algorithm;
import com.kpi.course.work.model.figures.Parallelepiped;

import java.util.*;
import java.util.stream.Collectors;

public class DynamicalAlgorithm extends Algorithm {

    private int[][] paths;
    private List<List<Parallelepiped>> allTowers;

    public DynamicalAlgorithm() {
        allTowers = new ArrayList<>();
    }

    public List<Parallelepiped> solve(List<Parallelepiped> parallelepipeds){
        long start = System.nanoTime();

        if (parallelepipeds.size() < 4) {
            return Collections.singletonList(findHighestParallelepiped(parallelepipeds));
        }

        matrix = buildMatrix(parallelepipeds);
        pathsInit();

        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][k] != NO_WAY && matrix[k][j] != NO_WAY && matrix[i][k] + matrix[k][j] > matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        paths[i][j] = paths[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != NO_WAY) {
                    allTowers.add(smartRemoveDuplications(findLongestPath(i, j, parallelepipeds)));
                }
            }
        }

        List<Parallelepiped> ls = searchHighestTower();
        long end= System.nanoTime();

        System.out.println(end-start);

        return ls;
    }



    private void findPath(int i, int j, List<Parallelepiped> path, List<Parallelepiped> ps) {
        if (paths[i][j] == -1 || paths[i][j] == i) {
            return;
        }

        path.add(ps.get(paths[i][j]));
        findPath(i, paths[i][j], path, ps);
    }

    private List<Parallelepiped> findLongestPath(int start, int end, List<Parallelepiped> ps) {
        List<Parallelepiped> path = new LinkedList<>();
        path.add(ps.get(end));

        findPath(start, end, path, ps);

        path.add(ps.get(start));
        return path;
    }

    private void pathsInit() {
        paths = new int[matrix.length][matrix.length];

        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths[i].length; j++) {
                if (i == j) {
                    paths[i][j] = 0;
                } else if (matrix[i][j] != NO_WAY) {
                    paths[i][j] = i;
                } else {
                    paths[i][j] = -1;
                }
            }
        }
    }

    private List<Parallelepiped> smartRemoveDuplications(List<Parallelepiped> parallelepipeds) {
        List<Parallelepiped> resultPath = new ArrayList<>();

        Map<String, List<Parallelepiped>> gropedPath = parallelepipeds.stream().collect(Collectors.groupingBy(Parallelepiped::getName));

        for (Map.Entry<String, List<Parallelepiped>> entry : gropedPath.entrySet()) {
            List<Parallelepiped> entryValue = entry.getValue();

            if (entryValue.size() > 1) {
                entryValue.sort(Comparator.comparing(Parallelepiped::getHeight));
                resultPath.add(entryValue.get(entryValue.size() - 1));
            } else {
                resultPath.addAll(entryValue);
            }
        }

        resultPath.sort(Comparator.comparing(Parallelepiped::getBase));
        return resultPath;
    }

    private List<Parallelepiped> searchHighestTower() {
        int max = 0;
        List<Parallelepiped> result = new ArrayList<>();

        for (List<Parallelepiped> parallelepipeds : allTowers) {
            int sum = parallelepipeds.stream().mapToInt(Parallelepiped::getHeight).sum();

            if (sum > max) {
                max = sum;
                result = parallelepipeds;
            }
        }

        return result;
    }
}

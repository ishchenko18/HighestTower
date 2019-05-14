package com.kpi.course.work.model.algorithms.greedy;

import com.kpi.course.work.model.algorithms.Algorithm;
import com.kpi.course.work.model.figures.Parallelepiped;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class GreedyAlgorithm extends Algorithm {

    public List<Parallelepiped> solve(List<Parallelepiped> parallelepipeds) {
        long start = System.nanoTime();

        if (parallelepipeds.size() < 4) {
            return Collections.singletonList(findHighestParallelepiped(parallelepipeds));
        }

        matrix = buildMatrix(parallelepipeds);

        int source = findSourceVertex(parallelepipeds);
        int destination = findDestinationVertex(parallelepipeds, parallelepipeds.get(source).getName());

        StringBuilder paths = new StringBuilder();
        findAllPaths(source, destination, paths, StringUtils.EMPTY, new boolean[matrix.length]);

        if (!paths.toString().isEmpty()) {
            Optional<Pair<String, Integer>> pairMaxPath = Arrays.stream(paths.toString().split("\n"))
                    .filter(p -> p.split("\\s").length <= parallelepipeds.size() / 3)
                    .filter(p -> isPossiblePath(parallelepipeds, p))
                    .map(path -> Pair.of(path, findHeightOfPyramid(parallelepipeds, path)))
                    .max(Comparator.comparing(Pair::getRight));

            String maxPath = pairMaxPath.isPresent() ? pairMaxPath.get().getLeft() : StringUtils.EMPTY;


            List<Parallelepiped> result = rebuildPath(parallelepipeds, maxPath);
            Collections.reverse(result);

            long end= System.nanoTime();

            System.out.println(end-start);

            return result;
        }

        return Collections.singletonList(findHighestParallelepiped(parallelepipeds));
    }

    private void findAllPaths(int start, int end, StringBuilder resultPaths, String currentPath, boolean[] visited) {
        String newPath = currentPath.isEmpty() ? String.format("%s", start) : String.format("%s %s", currentPath, start);
        visited[start] = true;

        for (int i = 0; i < matrix[start].length; i++) {
            if (matrix[start][i] != NO_WAY && i != end && !visited[i]) {
                findAllPaths(i, end, resultPaths, newPath, visited);
            } else if (i == end && matrix[start][i] != NO_WAY) {
                resultPaths.append(newPath).append(" ").append(i).append("\n");
            }
        }

        visited[start] = false;
    }

    private boolean isPossiblePath(List<Parallelepiped> parallelepipeds, String path) {
        String[] splittedPath = path.split("\\s");
        return Arrays.stream(splittedPath)
                .map(Integer::valueOf)
                .map(point -> parallelepipeds.get(point).getName())
                .distinct()
                .collect(Collectors.toList()).size() == splittedPath.length;
    }

    private int findHeightOfPyramid(List<Parallelepiped> parallelepipeds, String path) {
        return Arrays.stream(path.split("\\s"))
                .map(Integer::valueOf)
                .mapToInt(vertex -> parallelepipeds.get(vertex).getHeight())
                .sum();
    }

    private int findSourceVertex(List<Parallelepiped> parallelepipeds) {
        List<Parallelepiped> baseParallelepipeds = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            if (sumByColumn(i) == 0 && sumByRow(i) != 0) {
                baseParallelepipeds.add(parallelepipeds.get(i));
            }
        }

        baseParallelepipeds.sort(Comparator.comparing(Parallelepiped::getBase));
        return parallelepipeds.indexOf(baseParallelepipeds.get(baseParallelepipeds.size() - 1));
    }

    private int findDestinationVertex(List<Parallelepiped> parallelepipeds, String sourceName) {
        List<Parallelepiped> topParallelepipeds = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            if (sumByRow(i) == 0 && sumByColumn(i) != 0) {
                topParallelepipeds.add(parallelepipeds.get(i));
            }
        }

        topParallelepipeds.removeIf(p -> p.getName().equals(sourceName));
        topParallelepipeds.sort(Comparator.comparing(Parallelepiped::getBase));
        return parallelepipeds.indexOf(topParallelepipeds.get(0));
    }

    private int sumByColumn(int column) {
        int sum = 0;

        for (int[] array : matrix) {
            sum += array[column];
        }

        return sum;
    }

    private int sumByRow(int row) {
        return Arrays.stream(matrix[row]).sum();
    }

    private List<Parallelepiped> rebuildPath(List<Parallelepiped> parallelepipeds, String path) {
        return Arrays.stream(path.split("\\s"))
                .map(Integer::valueOf)
                .map(parallelepipeds::get)
                .collect(Collectors.toList());
    }
}

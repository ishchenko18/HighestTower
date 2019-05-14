package com.kpi.course.work;

import com.kpi.course.work.controllers.ApplicationController;
import com.kpi.course.work.model.figures.Parallelepiped;
import com.kpi.course.work.view.View;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            View view = new View();
            ApplicationController controller = new ApplicationController();
            view.printText("Please, choose algorithm for solution: ");
            view.printText("\t1 - Dynamical algorithm(based on Floyd-Warshell);");
            view.printText("\t2 - Greedy algorithm(based on DFS);\n");

            view.printString("Enter your variant: ");
            int algorithm = Integer.valueOf(view.readDataFromConsole());
            view.printText();

            view.printText("How to get data?");
            view.printText("\t1 - Read from file;");
            view.printText("\t2 - Generate randomly;\n");

            view.printString("Enter your variant: ");
            int dataPreparation = Integer.valueOf(view.readDataFromConsole());
            view.printText();

            if (isCorrectVariant(dataPreparation) || isCorrectVariant(algorithm)) {
                view.printError("Chose variant is incorrect. Rerun the program.");
                return;
            }

            Pair<List<Parallelepiped>, List<Parallelepiped>> pair = controller.solveTask(algorithm, dataPreparation);

            view.printText("Input data:");
            pair.getLeft().stream().map(Parallelepiped::toString).forEach(view::printText);

            view.printText();

            view.printText("Solution:");
            pair.getRight().stream().map(Parallelepiped::toString).forEach(view::printText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isCorrectVariant(int variant) {
        return variant > 2 || variant < 1;
    }
}

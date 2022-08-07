package tests;

import entities.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class TestOutput {
    public static void printExpands(LinkedHashSet<String> exploreMoves) {
        System.out.printf("%d%n", exploreMoves.size());
        for (String exploreMove : exploreMoves) {
            System.out.printf("%s%n", exploreMove);
        }
    }

    public static void printPath(Agent initialAgent, Agent targetAgent) {
        LinkedHashSet<String> pathMoves = new LinkedHashSet<>();
        if (targetAgent != null) {
            while (targetAgent != initialAgent) {
                pathMoves.add(targetAgent.state.getMove());
                targetAgent = targetAgent.parent;
            }
            System.out.printf("%d%n", pathMoves.size());
            List<String> reversePathMoves = new ArrayList<>(pathMoves);
            Collections.reverse(reversePathMoves);
            System.out.printf("%s%n", initialAgent.state.getMove());
            for (String pathMove : reversePathMoves) {
                System.out.printf("%s%n", pathMove);
            }
        }
    }
}

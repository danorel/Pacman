package tests;

import java.util.LinkedHashSet;

import entities.Agent;

public class TestOutput {
    public static void printExpands(LinkedHashSet<String> exploredMoves) {
        System.out.printf("%d%n", exploredMoves.size());
        for (String exploreMove : exploredMoves) {
            System.out.printf("%s%n", exploreMove);
        }
    }

    public static void printPath(Agent agent) {
        if (agent != null) {
            System.out.printf("%d%n", agent.size());
            Agent agentIter = agent.reverse();
            while (agentIter != null) {
                if (agentIter.state != null) {
                    System.out.printf("%s%n", agentIter.state.getMove());
                }
                agentIter = agentIter.parent;
            }
        }
    }
}

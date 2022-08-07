package entities;

public class Score {
    private static final int Epsilon = 1000;

    private static double costDistance(Agent currentAgent, Agent nextAgent) {
        return Math.abs(nextAgent.cost - currentAgent.cost);
    }

    private static double manhattanDistance(Agent currentAgent, Agent nextAgent) {
        return Math.abs((nextAgent.state.r - currentAgent.state.r) + Math.abs(nextAgent.state.c - currentAgent.state.c));
    }

    private static double euclideanDistance(Agent currentAgent, Agent nextAgent) {
        return Math.sqrt(Math.pow((nextAgent.state.r - currentAgent.state.r), 2) + Math.pow((nextAgent.state.c - currentAgent.state.c), 2));
    }

    public static double gScore(Agent initialAgent, Agent currentAgent) {
        return costDistance(initialAgent, currentAgent);
    }

    public static double hScore(Agent currentAgent, Agent targetAgent) {
        return manhattanDistance(currentAgent, targetAgent);
    }

    public static double fScore(Agent initialAgent, Agent currentAgent, Agent targetAgent) {
       return fScoreWeighted(initialAgent, currentAgent, targetAgent, 1, 1);
    }

    public static double fScoreWeighted(Agent initialAgent, Agent currentAgent, Agent targetAgent, double gWeight, double hWeight) {
        double g = gWeight * gScore(initialAgent, currentAgent);
        double h = hWeight * hScore(currentAgent, targetAgent);
        return (g + h) * Epsilon;
    }
}
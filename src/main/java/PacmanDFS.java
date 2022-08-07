import java.util.*;

import entities.Action;
import entities.Agent;
import entities.State;
import entities.World;
import tests.TestInput;

class PacmanDFS {

    private static void play(World world, Agent initialAgent) {
        int count = 0;
        ArrayList<Agent> expandedAgents = new ArrayList<>();

        Stack<Agent> stack = new Stack<>();
        stack.push(initialAgent);

        HashSet<String> visited = new HashSet<>();

        while (!stack.empty()) {
            Agent currentAgent = stack.pop();
            if (currentAgent == null) {
                continue;
            }
            ++count;
            expandedAgents.add(currentAgent);
            if (currentAgent.isGoal(world)) {
                System.out.printf("%d%n", count);
                for (Agent expandedAgent : expandedAgents) {
                    System.out.printf("%d %d%n", expandedAgent.state.r, expandedAgent.state.c);
                }
                return;
            }
            for (Action action : Action.values()) {
                Agent nextAgent = currentAgent.transition(world, action);
                if (nextAgent != null) {
                    String nextState = String.valueOf(nextAgent.state);
                    if (!visited.contains(nextState)) {
                        stack.push(nextAgent);
                        visited.add(nextState);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(TestInput.TEST_3);

        int packmanr = scanner.nextInt();
        int packmanc = scanner.nextInt();

        int foodr = scanner.nextInt();
        int foodc = scanner.nextInt();

        int width = scanner.nextInt();
        int height = scanner.nextInt();

        int r = 0;
        char[][] grid = new char[width][height];
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int c = 0; c < row.length(); c++) {
                grid[r][c] = row.charAt(c);
            }
            ++r;
        }

        World world = new World(grid, width, height);
        Agent initialAgent = new Agent(new State(packmanr, packmanc));

        play(world, initialAgent);
    }
}
import java.util.*;

import entities.Action;
import entities.Agent;
import entities.State;
import entities.World;
import tests.TestInput;

class PacmanDFS {

    private static Agent play(World world, Agent initialAgent) {
        LinkedHashSet<String> popMoves = new LinkedHashSet<>();
        LinkedHashSet<String> pushMoves = new LinkedHashSet<>();

        Stack<Agent> stack = new Stack<>();
        stack.push(initialAgent);
        pushMoves.add(initialAgent.state.getMove());

        while (!stack.empty()) {
            Agent currentAgent = stack.pop();
            popMoves.add(currentAgent.state.getMove());
            if (currentAgent.isGoal(world)) {
                System.out.printf("%d%n", popMoves.size());
                for (String popMove : popMoves) {
                    System.out.printf("%s%n", popMove);
                }
                return currentAgent;
            }
            for (Action action : Action.values()) {
                Agent nextAgent = currentAgent.transition(world, action);
                if (nextAgent != null) {
                    String nextMove = nextAgent.state.getMove();
                    if (!pushMoves.contains(nextMove)) {
                        pushMoves.add(nextAgent.state.getMove());
                        stack.push(nextAgent);
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(TestInput.TEST_1);

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
        Agent targetAgent = play(world, initialAgent);

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
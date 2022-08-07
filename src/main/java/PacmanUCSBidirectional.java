import entities.*;
import tests.TestInput;
import tests.TestOutput;

import java.util.*;

class PacmanUCSBidirectional {

    private static Agent expand(World world, Agent initialAgent, Queue<Agent> queue, LinkedHashMap<String, Agent> currentAgentVisitedMoves, LinkedHashMap<String, Agent> anotherAgentVisitedMoves, LinkedHashSet<String> exploreMoves, Agent minPathAgent) {
        Agent currentAgent = queue.poll();

        assert currentAgent != null;
        exploreMoves.add(currentAgent.state.getMove());

        for (Action action : Action.values()) {
            Agent nextAgent = currentAgent.transition(world, action);
            if (nextAgent == null) {
                continue;
            }
            String nextMove = nextAgent.state.getMove();
            Agent prevAgent = currentAgentVisitedMoves.get(nextMove);
            if (prevAgent == null) {
                prevAgent = nextAgent;
            }
            double nextCost = Score.gScore(initialAgent, nextAgent);
            double prevCost = prevAgent.cost;
            if (!currentAgentVisitedMoves.containsKey(nextMove) || nextCost < prevCost) {
                queue.add(nextAgent);
                currentAgentVisitedMoves.put(nextMove, nextAgent);
                Agent anotherAgent = anotherAgentVisitedMoves.get(nextMove);
                if (anotherAgent == null) {
                    continue;
                }
                Agent possiblePathAgent = initialAgent.merge(world, nextAgent, anotherAgent);
                if (minPathAgent == null) {
                    minPathAgent = possiblePathAgent;
                } else {
                    if (possiblePathAgent.cost < minPathAgent.cost) {
                        minPathAgent = possiblePathAgent;
                    }
                }
            }
        }

        return minPathAgent;
    }

    private static void play(World world, Agent pacmanAgent, Agent foodAgent) {
        LinkedHashSet<String> exploreMoves = new LinkedHashSet<>();

        LinkedHashMap<String, Agent> pacmanVisitedMoves = new LinkedHashMap<>();
        pacmanVisitedMoves.put(pacmanAgent.state.getMove(), pacmanAgent);
        Queue<Agent> pacmanQueue = new LinkedList<>();
        pacmanQueue.add(pacmanAgent);

        LinkedHashMap<String, Agent> foodVisitedMoves = new LinkedHashMap<>();
        foodVisitedMoves.put(foodAgent.state.getMove(), foodAgent);
        Queue<Agent> foodQueue = new LinkedList<>();
        foodQueue.add(foodAgent);

        Agent minPathAgent = null;
        while (pacmanQueue.size() > 0 && foodQueue.size() > 0) {
            Agent pacmanTopAgent = pacmanQueue.peek();
            Agent foodTopAgent = foodQueue.peek();

            if (pacmanTopAgent.cost < foodTopAgent.cost) {
                minPathAgent = expand(world, pacmanAgent, pacmanQueue, pacmanVisitedMoves, foodVisitedMoves, exploreMoves, minPathAgent);
            } else {
                minPathAgent = expand(world, foodAgent, foodQueue, foodVisitedMoves, pacmanVisitedMoves, exploreMoves, minPathAgent);
            }
        }
        TestOutput.printExpands(exploreMoves);
        TestOutput.printPath(pacmanAgent, minPathAgent);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(TestInput.TEST_5);

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
        Agent foodAgent = new Agent(new State(foodr, foodc));
        Agent pacmanAgent = new Agent(new State(packmanr, packmanc));

        play(world, pacmanAgent, foodAgent);
    }
}
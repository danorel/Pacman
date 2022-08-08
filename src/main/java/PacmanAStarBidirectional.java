import entities.*;
import tests.TestInput;
import tests.TestOutput;

import java.util.*;

class PacmanAStarBidirectional {

    private static Agent expand(World world, Agent thisAgent, Queue<Agent> thisQueue, LinkedHashMap<String, Agent> thisAgentMoves, Agent thatAgent, LinkedHashMap<String, Agent> thatAgentMoves, LinkedHashSet<String> exploredMoves, Agent minAgent) {
        Agent thisCurrentAgent = thisQueue.poll();
        assert(thisCurrentAgent != null);

        exploredMoves.add(thisCurrentAgent.state.getMove());

        for (Action action : Action.values()) {
            Agent thisNextAgent = thisCurrentAgent.transition(world, action);
            if (thisNextAgent == null || thisNextAgent.state == null) {
                continue;
            }
            String thisNextMove = thisNextAgent.state.getMove();
            Agent thisPrevAgent = thisAgentMoves.get(thisNextMove);
            if (thisPrevAgent == null) {
                thisPrevAgent = thisNextAgent;
            }
            double thisNextCost = Score.fScore(thisAgent, thisNextAgent, thatAgent);
            double thisPrevCost = Score.fScore(thisAgent, thisPrevAgent, thatAgent);
            if (!thisAgentMoves.containsKey(thisNextMove) || thisNextCost < thisPrevCost) {
                thisQueue.add(thisNextAgent);
                thisAgentMoves.put(thisNextMove, thisNextAgent);
                Agent thatPrevAgent = thatAgentMoves.get(thisNextMove);
                if (thatPrevAgent == null) {
                    continue;
                }
                Agent possibleMinAgent = thisAgent.merge(thisNextAgent, thatPrevAgent);
                if (minAgent == null) {
                    minAgent = possibleMinAgent;
                } else {
                    if (possibleMinAgent.cost < minAgent.cost) {
                        minAgent = possibleMinAgent;
                    }
                }
            }
        }

        return minAgent;
    }

    private static void play(World world, Agent pacmanAgent, Agent foodAgent) {
        LinkedHashSet<String> exploredMoves = new LinkedHashSet<>();

        LinkedHashMap<String, Agent> pacmanAgentMoves = new LinkedHashMap<>();
        pacmanAgentMoves.put(pacmanAgent.state.getMove(), pacmanAgent);
        Queue<Agent> pacmanQueue = new PriorityQueue<>((o1, o2) -> (int) (Score.fScore(pacmanAgent, o2, foodAgent) - Score.fScore(pacmanAgent, o1, foodAgent)));
        pacmanQueue.add(pacmanAgent);

        LinkedHashMap<String, Agent> foodAgentMoves = new LinkedHashMap<>();
        foodAgentMoves.put(foodAgent.state.getMove(), foodAgent);
        Queue<Agent> foodQueue = new PriorityQueue<>((o1, o2) -> (int) (Score.fScore(foodAgent, o2, pacmanAgent) - Score.fScore(foodAgent, o1, pacmanAgent)));
        foodQueue.add(foodAgent);

        Agent minAgent = null;
        while (pacmanQueue.size() > 0 && foodQueue.size() > 0) {
            Agent pacmanTop = pacmanQueue.peek();
            Agent foodTop = foodQueue.peek();

            if (pacmanTop.cost < foodTop.cost) {
                minAgent = expand(world, pacmanAgent, pacmanQueue, pacmanAgentMoves, foodAgent, foodAgentMoves, exploredMoves, minAgent);
            } else {
                minAgent = expand(world, foodAgent, foodQueue, foodAgentMoves, pacmanAgent, pacmanAgentMoves, exploredMoves, minAgent);
            }
        }

        TestOutput.printPath(minAgent);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(TestInput.TEST_5);

        int pacmanr = scanner.nextInt();
        int pacmanc = scanner.nextInt();

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
        Agent pacmanAgent = new Agent(new State(pacmanr, pacmanc));

        play(world, pacmanAgent, foodAgent);
    }
}
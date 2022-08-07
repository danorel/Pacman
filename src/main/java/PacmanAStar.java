import entities.*;

import java.util.*;

class PacmanAStar {

    private static void play(World world, Agent initialAgent, Agent targetAgent) {
        int count = 0;
        ArrayList<Agent> expandedAgents = new ArrayList<>();

        PriorityQueue<Agent> queue = new PriorityQueue<>((prevAgent, nextAgent) -> (int) (Score.fScore(initialAgent, prevAgent, targetAgent, 1, 0.1) - Score.fScore(initialAgent, nextAgent, targetAgent, 1, 0.1)));
        queue.add(initialAgent);

        HashSet<String> visited = new HashSet<>();

        while (queue.size() > 0) {
            Agent currentAgent = queue.poll();
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
                        queue.add(nextAgent);
                        visited.add(nextState);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * 3 9
         * 5 1
         * 7 20
         * %%%%%%%%%%%%%%%%%%%%
         * %--------------%---%
         * %-%%-%%-%%-%%-%%-%-%
         * %--------P-------%-%
         * %%%%%%%%%%%%%%%%%%-%
         * %.-----------------%
         * %%%%%%%%%%%%%%%%%%%%
         */
        String test1 = "3 9 5 1 7 20 %%%%%%%%%%%%%%%%%%%% %--------------%---% %-%%-%%-%%-%%-%%-%-% %--------P-------%-% %%%%%%%%%%%%%%%%%%-% %.-----------------% %%%%%%%%%%%%%%%%%%%%";

        Scanner scanner = new Scanner(test1);

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
        Agent targetAgent = new Agent(new State(foodr, foodc));
        Agent initialAgent = new Agent(new State(packmanr, packmanc));

        play(world, initialAgent, targetAgent);
    }
}
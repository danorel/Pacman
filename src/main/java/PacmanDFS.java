import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class PacmanDFS {
    private static class World {
        final static char Pacman = 'P';
        final static char Wall = '%';
        final static char Food = '.';

        char[][] grid;
        int width;
        int height;

        public World(char[][] grid, int width, int height) {
            this.grid = grid;
            this.width = width;
            this.height = height;
        }

        public boolean isInBounds(int r, int c) {
            return r >= 0 && r < width && c >= 0 && c < height;
        }

        public boolean isWall(int r, int c) {
            return this.grid[r][c] == Wall;
        }

        public boolean isFood(int r, int c) {
            return this.grid[r][c] == Food;
        }
    }

    private static enum Action {
        Right("RIGHT"),
        Left("LEFT"),
        Up("UP"),
        Down("DOWN");

        private String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class State {
        int r;
        int c;

        public State(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "State{" +
                    "r=" + r +
                    ", c=" + c +
                    '}';
        }
    }

    private static class Agent {
        State state;
        int score;
        Agent parent;
        Action action;

        public Agent(State state) {
            this(state, 0, null, null);
        }

        public Agent(State state, int score, Agent parent, Action action) {
            this.state = state;
            this.score = score;
            this.parent = parent;
            this.action = action;
        }

        public boolean canMove(World world, Action action) {
            return isInsideWorld(world, action) && !isNextToWall(world, action);
        }

        private boolean isNextToWall(World world, Action action) {
            switch (action) {
                case Up: {
                    return world.isWall(this.state.r - 1, this.state.c);
                }
                case Down: {
                    return world.isWall(this.state.r + 1, this.state.c);
                }
                case Left: {
                    return world.isWall(this.state.r, this.state.c - 1);
                }
                case Right: {
                    return world.isWall(this.state.r, this.state.c + 1);
                }
                default: {
                    throw new RuntimeException("Unknown action " + action);
                }
            }
        }

        private boolean isInsideWorld(World world, Action action) {
            switch (action) {
                case Up: {
                    return world.isInBounds(this.state.r - 1, this.state.c);
                }
                case Down: {
                    return world.isInBounds(this.state.r + 1, this.state.c);
                }
                case Left: {
                    return world.isInBounds(this.state.r, this.state.c - 1);
                }
                case Right: {
                    return world.isInBounds(this.state.r, this.state.c + 1);
                }
                default: {
                    throw new RuntimeException("Unknown action " + action);
                }
            }
        }

        @Override
        public String toString() {
            return "Agent{" +
                    "state=" + state +
                    ", score=" + score +
                    ", parent=" + parent +
                    ", action=" + action +
                    '}';
        }
    }

    private static int gScore(Agent prevAgent, Agent nextAgent) {
        return nextAgent.score - prevAgent.score;
    }

    private static int fScore(Agent prevAgent, Agent nextAgent) {
        return gScore(prevAgent, nextAgent);
    }

    private static Agent transitionModel(World world, Agent currentAgent, Action action) {
        if (!currentAgent.canMove(world, action)) {
            return null;
        }
        switch (action) {
            case Up: {
                return new Agent(new State(currentAgent.state.r - 1, currentAgent.state.c), currentAgent.score + 1, currentAgent, action);
            }
            case Down: {
                return new Agent(new State(currentAgent.state.r + 1, currentAgent.state.c), currentAgent.score + 1, currentAgent, action);
            }
            case Left: {
                return new Agent(new State(currentAgent.state.r, currentAgent.state.c - 1), currentAgent.score + 1, currentAgent, action);
            }
            case Right: {
                return new Agent(new State(currentAgent.state.r, currentAgent.state.c + 1), currentAgent.score + 1, currentAgent, action);
            }
            default: {
                return new Agent(new State(currentAgent.state.r, currentAgent.state.c), currentAgent.score, currentAgent, action);
            }
        }
    }

    private static boolean isGoal(World world, Agent currentAgent) {
        return world.isFood(currentAgent.state.r, currentAgent.state.c);
    }

    private static void play(World world, Agent initialAgent) {
        int count = 0;
        ArrayList<Agent> expandedAgents = new ArrayList<>();

        Stack<Agent> stack = new Stack<Agent>();
        stack.push(initialAgent);

        while (!stack.empty()) {
            Agent currentAgent = stack.pop();
            if (currentAgent == null) {
                continue;
            }
            ++count;
            expandedAgents.add(currentAgent);
            if (isGoal(world, currentAgent)) {
                System.out.printf("%d%n", count);
                for (Agent expandedAgent : expandedAgents) {
                    System.out.printf("%d %d%n", expandedAgent.state.r, expandedAgent.state.c);
                }
                return;
            }
            for (Action action : Action.values()) {
                Agent nextAgent = transitionModel(world, currentAgent, action);
                stack.push(nextAgent);
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
        }

        World world = new World(grid, width, height);
        Agent initialAgent = new Agent(new State(packmanr, packmanc));

        play(world, initialAgent);
    }
}
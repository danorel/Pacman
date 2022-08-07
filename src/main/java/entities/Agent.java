package entities;

public class Agent {
    public State state;
    public double cost;
    public Agent parent;
    public Action action;

    public Agent(State state) {
        this(state, 0., null, null);
    }

    public Agent(State state, double cost, Agent parent, Action action) {
        this.state = state;
        this.cost = cost;
        this.parent = parent;
        this.action = action;
    }

    public Agent transition(World world, Action action) {
        if (!canMove(world, action)) {
            return null;
        }
        switch (action) {
            case Up: {
                return new Agent(new State(this.state.r - 1, this.state.c), this.cost + 1, this, action);
            }
            case Down: {
                return new Agent(new State(this.state.r + 1, this.state.c), this.cost + 1, this, action);
            }
            case Left: {
                return new Agent(new State(this.state.r, this.state.c - 1), this.cost + 1, this, action);
            }
            case Right: {
                return new Agent(new State(this.state.r, this.state.c + 1), this.cost + 1, this, action);
            }
            default: {
                return new Agent(new State(this.state.r, this.state.c), this.cost, this, action);
            }
        }
    }

    public boolean isGoal(World world) {
        return world.isFood(this.state.r, this.state.c);
    }

    private boolean canMove(World world, Action action) {
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
                ", cost=" + cost +
                ", parent=" + parent +
                ", action=" + action +
                '}';
    }
}

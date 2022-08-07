package entities;

import java.util.Stack;

public class Agent {
    public State state;
    public double cost;
    public Agent parent;
    public Action action;

    public Agent() { this(null, 0., null, null ); }

    public Agent(State state) {
        this(state, 0., null, null);
    }

    public Agent(State state, double cost, Agent parent, Action action) {
        this.state = state;
        this.cost = cost;
        this.parent = parent;
        this.action = action;
    }

    public Agent(Agent thatAgent) {
        if (thatAgent != null) {
            Agent thisIterAgent = this;
            Agent thatIterAgent = thatAgent;

            while (thatIterAgent != null) {
                thisIterAgent.state = new State(thatIterAgent.state.r, thatIterAgent.state.c);
                thisIterAgent.cost = thatIterAgent.cost;
                thisIterAgent.action = thatIterAgent.action;
                thisIterAgent.parent = new Agent();
                thatIterAgent = thatIterAgent.parent;
                thisIterAgent = thisIterAgent.parent;
            }
        }
    }

    public int size() {
        if (this.parent == null) {
            return 0;
        }
        int size = 0;
        Agent thisIterAgent = this.parent;
        while (thisIterAgent.parent != null) {
            ++size;
            thisIterAgent = thisIterAgent.parent;
        }
        return size - 1;
    }

    public Agent reverse() {
        Agent copy = new Agent();

        Stack<Agent> stack = new Stack<>();

        Agent thatIterAgent = this;
        while (thatIterAgent != null) {
            stack.push(thatIterAgent);
            thatIterAgent = thatIterAgent.parent;
        }

        Agent thisIterAgent = copy;
        while (!stack.empty()) {
            thatIterAgent = stack.pop();
            if (thatIterAgent.state != null) {
                thisIterAgent.state = new State(thatIterAgent.state.r, thatIterAgent.state.c);
            }
            thisIterAgent.cost = thatIterAgent.cost;
            thisIterAgent.action = thatIterAgent.action;
            thisIterAgent.parent = new Agent();
            thisIterAgent = thisIterAgent.parent;
        }

        return copy;
    }

    public Agent merge(Agent commonAgent, Agent thatAgent) {
        Agent mergeAgent = thatAgent.reverse();

        Agent mergeIterAgent = mergeAgent;
        while (mergeIterAgent.parent != null) {
            mergeIterAgent = mergeIterAgent.parent;
        }

        mergeIterAgent.parent = new Agent(commonAgent);

        Agent commonIterAgent = commonAgent;
        while ((commonIterAgent.state.r != this.state.r || commonIterAgent.state.c != this.state.c)) {
            commonIterAgent = commonIterAgent.parent;
            Action mergeAction = null;
            if (commonIterAgent.action != null) {
                mergeAction = commonIterAgent.action.opposite();
            }
            double mergeCost = mergeIterAgent.cost;
            mergeIterAgent = mergeIterAgent.parent;
            mergeIterAgent.cost = mergeCost + 1;
            mergeIterAgent.action = mergeAction;
        }

        return mergeAgent;
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
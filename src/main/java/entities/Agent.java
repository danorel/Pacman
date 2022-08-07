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

    public Agent(Agent anotherAgent) {
        if (anotherAgent != null) {
            Stack<Agent> stack = new Stack<>();

            Agent headAgent = anotherAgent;

            while (headAgent.parent != null) {
                stack.add(headAgent);
                headAgent = headAgent.parent;
            }

            this.state = new State(headAgent.state.r, headAgent.state.c);
            Agent copyAgent = this;

            while (!stack.empty()) {
                Agent topAgent = stack.pop();
                Agent nextAgent = new Agent();
                copyAgent.parent = new Agent(new State(topAgent.state.r, topAgent.state.c), topAgent.cost, nextAgent, topAgent.action);
                copyAgent = copyAgent.parent;
            }
        }
    }

    public Agent merge(World world, Agent commonAgent, Agent thatAgent) {
        Agent mergeAgent = new Agent(thatAgent);

        Agent mergeIterationAgent = mergeAgent;
        while (mergeIterationAgent.state.r != commonAgent.state.r || mergeIterationAgent.state.c != commonAgent.state.c) {
            mergeIterationAgent = mergeIterationAgent.parent;
        }

        mergeIterationAgent.parent = commonAgent.parent;

        Agent commonIterationAgent = commonAgent;
        while ((commonIterationAgent.state.r != this.state.r || commonIterationAgent.state.c != this.state.c)) {
            commonIterationAgent = commonIterationAgent.parent;
            Action mergeAction = null;
            if (commonIterationAgent.action != null) {
                mergeAction = commonIterationAgent.action.opposite();
            }
            double mergeCost = mergeIterationAgent.cost;
            mergeIterationAgent = mergeIterationAgent.parent;
            mergeIterationAgent.cost = mergeCost + 1;
            mergeIterationAgent.action = mergeAction;
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
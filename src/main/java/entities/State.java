package entities;

public class State {
    public int r;
    public int c;

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

package entities;

public enum Action {
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

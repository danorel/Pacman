package entities;

public enum Action {
    Up("UP"),
    Left("LEFT"),
    Right("RIGHT"),
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
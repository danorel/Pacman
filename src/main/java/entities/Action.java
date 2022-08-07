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

    public Action opposite() {
        switch (this) {
            case Up: {
                return Down;
            }
            case Down: {
                return Up;
            }
            case Left: {
                return Right;
            }
            case Right: {
                return Left;
            }
            default: {
                throw new RuntimeException("Unknown action: " + this);
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
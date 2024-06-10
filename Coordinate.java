public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinate coordinate) {
            return x == coordinate.x && y == coordinate.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}

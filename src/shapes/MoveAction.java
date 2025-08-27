package shapes;

import java.awt.Point;

public class MoveAction {
    private Shape shape;
    private Point previousPosition;
    private int dx, dy;

    public MoveAction(Shape shape, Point previousPosition, int dx, int dy) {
        this.shape = shape;
        this.previousPosition = previousPosition;
        this.dx = dx;
        this.dy = dy;
    }

    public Shape getShape() {
        return shape;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}

package shapes;

public class UndoableDeleteAction {
    private Shape deletedShape;

    public UndoableDeleteAction(Shape deletedShape) {
        this.deletedShape = deletedShape;
    }

    public Shape getDeletedShape() {
        return deletedShape;
    }
}

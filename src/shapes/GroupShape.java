package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GroupShape implements Shape {
    private List<Shape> shapes;  // The list of shapes in this group
    private Color color;
    private Point position; // To track the position of the group (used for moving)

    public GroupShape(Color color) {
        this.shapes = new ArrayList<>();
        this.color = color;
        this.position = new Point(0, 0);  // Default position
    }

    // Add a shape to the group
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Get the shapes inside the group
    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    public void draw(Graphics g) {
        // Draw each shape in the group
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    @Override
    public boolean contains(Point p) {
        // Check if any of the shapes in the group contains the point
        for (Shape shape : shapes) {
            if (shape.contains(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(int dx, int dy) {
        // Move each shape inside the group
        for (Shape shape : shapes) {
            shape.move(dx, dy);
        }
        // Update the position of the group itself
        position.translate(dx, dy);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getX() {
        // Return the X coordinate of the first shape (or use group position)
        return position.x;
    }

    @Override
    public int getY() {
        // Return the Y coordinate of the first shape (or use group position)
        return position.y;
    }

    @Override
    public int getEndX() {
        // Return the X coordinate of the last shape
        return shapes.get(shapes.size() - 1).getEndX();
    }

    @Override
    public int getEndY() {
        // Return the Y coordinate of the last shape
        return shapes.get(shapes.size() - 1).getEndY();
    }

	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
}

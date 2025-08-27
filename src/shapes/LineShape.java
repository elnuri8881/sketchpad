package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LineShape extends AbstractShape {
    private Point start, end;

    public LineShape(Point start, Point end, Color color) {
        super(color);
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public boolean contains(Point p) {
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        int px = p.x - start.x;
        int py = p.y - start.y;
        double lineLength = Math.sqrt(dx * dx + dy * dy);
        double distance = Math.abs(dy * px - dx * py) / lineLength;
        return distance < 5;  // Tolerance for selecting the line
    }

    @Override
    public void move(int dx, int dy) {
        start.translate(dx, dy);
        end.translate(dx, dy);
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
    public void setPosition(Point p) {
        // Set the start point and end point based on the new position
        this.start = p;
        this.end = new Point(p.x + 100, p.y);  // Example: move 100 units in the x direction
    }
    
    @Override
    public int getX() {
        return start.x; // Returning the X coordinate of the starting point
    }

    @Override
    public int getY() {
        return start.y; // Returning the Y coordinate of the starting point
    }
    
    @Override
    public int getEndX() {
        return end.x;
    }

    @Override
    public int getEndY() {
        return end.y;
    }
}

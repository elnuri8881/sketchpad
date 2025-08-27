package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class EllipseShape extends AbstractShape {
    private Point start, end;

    // Constructor
    public EllipseShape(Point start, Point end, Color color) {
        super(color);  // Call the constructor of AbstractShape
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(Math.min(start.x, end.x),
                   Math.min(start.y, end.y),
                   Math.abs(end.x - start.x),
                   Math.abs(end.y - start.y));  // Draw the ellipse
    }

    @Override
    public boolean contains(Point p) {
        // Ellipse contains logic (you can refine this logic)
        double rx = Math.abs(end.x - start.x) / 2.0;
        double ry = Math.abs(end.y - start.y) / 2.0;
        double cx = (start.x + end.x) / 2.0;
        double cy = (start.y + end.y) / 2.0;
        
        // Check if the point (p) lies inside the ellipse
        return ((Math.pow(p.x - cx, 2) / Math.pow(rx, 2)) + (Math.pow(p.y - cy, 2) / Math.pow(ry, 2)) <= 1);
    }

    @Override
    public void move(int dx, int dy) {
        start.translate(dx, dy);
        end.translate(dx, dy);
    }

    // Getter for color
    @Override
    public Color getColor() {
        return color;
    }

    // Setter for color
    @Override
    public void setColor(Color color) {
        this.color = color;
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

	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
}

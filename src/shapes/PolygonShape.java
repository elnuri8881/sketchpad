package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class PolygonShape extends AbstractShape {
    private Polygon polygon;

    // Constructor
    public PolygonShape(Polygon polygon, Color color) {
        super(color);  // Call the constructor of AbstractShape
        this.polygon = polygon;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawPolygon(polygon);  // Draw the polygon using the Polygon object
    }

    @Override
    public boolean contains(Point p) {
        return polygon.contains(p);  // Check if the point is inside the polygon
    }

    @Override
    public void move(int dx, int dy) {
        // Move each point in the polygon by dx and dy
        for (int i = 0; i < polygon.npoints; i++) {
            polygon.xpoints[i] += dx;
            polygon.ypoints[i] += dy;
        }
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
        return polygon.xpoints[0];  // Return X of the first point
    }
    
    @Override
    public int getY() {
        return polygon.ypoints[0];  // Return Y of the first point
    }

    @Override
    public int getEndX() {
        return polygon.xpoints[polygon.npoints - 1];  // Last point's X
    }

    @Override
    public int getEndY() {
        return polygon.ypoints[polygon.npoints - 1];  // Last point's Y
    }
    
    public Polygon getPolygon() {
        return polygon;  // Return the Polygon object
    }
    
	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
    
}

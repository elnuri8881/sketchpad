package shapes;

import java.awt.*;
import java.util.List;

public class FreehandShape implements Shape {
    private List<Point> points;
    private Color color;

   
    public FreehandShape(List<Point> points, Color color) {
        this.points = points;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public boolean contains(Point p) {
        for (Point pt : points) {
            if (pt.distance(p) < 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(int dx, int dy) {
        for (Point p : points) {
            p.translate(dx, dy);
        }
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
        if (!points.isEmpty()) {
            return points.get(0).x;  // Return the X of the first point
        }
        return 0;  // Default value if points list is empty
    }
    
    @Override
    public int getY() {
        if (!points.isEmpty()) {
            return points.get(0).y;  // Return the Y of the first point
        }
        return 0;  // Default value if points list is empty
    }
    
    @Override
    public int getEndX() {
        return points.get(points.size() - 1).x;  // Last point's X
    }

    @Override
    public int getEndY() {
        return points.get(points.size() - 1).y;  // Last point's Y
    }

    public List<Point> getPoints() {
        return points;  // Return the list of points
    }
    
	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
    
}

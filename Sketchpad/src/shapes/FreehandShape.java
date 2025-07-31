package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class FreehandShape extends AbstractShape {
    private List<Point> points; 
  
    public FreehandShape(List<Point> points, Color color) {
        super(color); 
        this.points = points;
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
}
 	
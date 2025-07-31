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
}

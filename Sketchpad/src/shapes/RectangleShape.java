package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class RectangleShape extends AbstractShape {
    private Point start, end;

    
    public RectangleShape(Point start, Point end, Color color) {
        super(color);  
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(Math.min(start.x, end.x),
                   Math.min(start.y, end.y),
                   Math.abs(end.x - start.x),
                   Math.abs(end.y - start.y)); 
    }
}

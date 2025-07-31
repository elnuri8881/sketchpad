package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class PolygonShape extends AbstractShape {
    private Polygon polygon;

   
    public PolygonShape(Polygon polygon, Color color) {
        super(color); 
        this.polygon = polygon;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawPolygon(polygon); 
    }
}

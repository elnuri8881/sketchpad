package shapes;

import java.awt.Color;
import java.awt.Point;

public abstract class AbstractShape implements Shape {
    protected Color color;

    
    public AbstractShape(Color color) {
        this.color = color;
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
    public abstract void setPosition(Point p);  
}

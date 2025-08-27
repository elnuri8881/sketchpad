package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Shape {
    void draw(Graphics g); 
    
    boolean contains(Point p);
    
    void move(int dx, int dy);
    
    Color getColor();
    
    void setColor(Color color);
    
    void setPosition(Point p);
    
    int getX();
    
    int getY();
    
    int getEndX();
    
    int getEndY();
    
}

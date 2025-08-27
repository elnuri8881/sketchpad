package shapes;

import java.awt.*;

public class RectangleShape implements Shape {
    private Point topLeft;
    private Point bottomRight;
    private Color color;

    // Constructor
    public RectangleShape(Point topLeft, Point bottomRight, Color color) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= topLeft.x && p.x <= bottomRight.x && p.y >= topLeft.y && p.y <= bottomRight.y;
    }

    @Override
    public void move(int dx, int dy) {
        topLeft.translate(dx, dy);
        bottomRight.translate(dx, dy);
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
        return topLeft.x;
    }

    @Override
    public int getY() {
        return topLeft.y;
    }
    
    @Override
    public int getEndX() {
        return bottomRight.x;
    }

    @Override
    public int getEndY() {
        return bottomRight.y;
    }

	@Override
	public void setPosition(Point p) {
		// TODO Auto-generated method stub
		
	}
    
}

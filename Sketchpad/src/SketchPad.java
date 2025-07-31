import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shapes.*;
import shapes.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SketchPad extends JFrame {

    private enum Mode { FREEHAND, LINE, RECTANGLE, ELLIPSE, POLYGON }

    private Mode currentMode = Mode.FREEHAND;
    private Color currentColor = Color.BLACK;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Stack<Shape> undoStack = new Stack<>();
    private Stack<Shape> redoStack = new Stack<>();
    private Point startPoint = null;
    private Point endPoint = null;
    private Polygon currentPolygon = new Polygon();

    
    private List<Point> currentFreehandPoints = new ArrayList<>();  // Points for the current freehand shape

    public SketchPad() {
        setTitle("SketchPad Drawing Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        add(canvas, BorderLayout.CENTER);

        
        JPanel toolbar = new JPanel();
        JButton freehandButton = new JButton("Freehand");
        JButton lineButton = new JButton("Line");
        JButton rectangleButton = new JButton("Rectangle");
        JButton ellipseButton = new JButton("Ellipse");
        JButton colorButton = new JButton("Color");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        toolbar.add(freehandButton);
        toolbar.add(lineButton);
        toolbar.add(rectangleButton);
        toolbar.add(ellipseButton);
        toolbar.add(colorButton);
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        add(toolbar, BorderLayout.NORTH);

        
        freehandButton.addActionListener(e -> currentMode = Mode.FREEHAND);
        lineButton.addActionListener(e -> currentMode = Mode.LINE);
        rectangleButton.addActionListener(e -> currentMode = Mode.RECTANGLE);
        ellipseButton.addActionListener(e -> currentMode = Mode.ELLIPSE);
        colorButton.addActionListener(e -> {
            currentColor = JColorChooser.showDialog(this, "Choose Color", currentColor);
        });

        
        undoButton.addActionListener(e -> undo());

       
        redoButton.addActionListener(e -> redo());

        setVisible(true);
    }

    private class Canvas extends JComponent {
        public Canvas() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint(); 
                    if (currentMode == Mode.FREEHAND) {
                        currentFreehandPoints.clear();  
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    endPoint = e.getPoint();  
                    Shape shape = null;
                    switch (currentMode) {
                        case FREEHAND:
                            shape = new FreehandShape(new ArrayList<>(currentFreehandPoints), currentColor);  
                            break;
                        case LINE:
                            shape = new LineShape(startPoint, endPoint, currentColor); 
                            break;
                        case RECTANGLE:
                            shape = new RectangleShape(startPoint, endPoint, currentColor); 
                            break;
                        case ELLIPSE:
                            shape = new EllipseShape(startPoint, endPoint, currentColor);  
                            break;
                    }

                    if (shape != null) {
                        shapes.add(shape);  
                        undoStack.push(shape);  
                        redoStack.clear(); 
                    }
                    repaint();  
                    startPoint = null;  
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    endPoint = e.getPoint(); 
                    if (currentMode == Mode.FREEHAND) {
                        currentFreehandPoints.add(endPoint); 
                    }
                    repaint(); 
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw all the shapes
            for (Shape shape : shapes) {
                shape.draw(g); 
            }

            
            if (currentMode == Mode.LINE && startPoint != null) {
                g.setColor(currentColor);
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);  
            }


            if (currentMode == Mode.RECTANGLE && startPoint != null) {
                g.setColor(currentColor);
                g.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y)); 
            }

            
            if (currentMode == Mode.ELLIPSE && startPoint != null) {
                g.setColor(currentColor);
                g.drawOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y)); 
            }


            if (currentMode == Mode.FREEHAND) {
                g.setColor(currentColor);
                for (int i = 1; i < currentFreehandPoints.size(); i++) {
                    Point p1 = currentFreehandPoints.get(i - 1);
                    Point p2 = currentFreehandPoints.get(i);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);  
                }
            }
        }
    }

   
    private void undo() {
        if (!undoStack.isEmpty()) {
            Shape lastShape = undoStack.pop();  
            shapes.remove(lastShape); 
            redoStack.push(lastShape);  
            repaint();  
        }
    }

 
    private void redo() {
        if (!redoStack.isEmpty()) {
            Shape lastShape = redoStack.pop();  
            shapes.add(lastShape);
            undoStack.push(lastShape); 
            repaint();  
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SketchPad::new);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import shapes.*;
import shapes.Shape;

public class SketchPad extends JFrame {

    private enum Mode { FREEHAND, LINE, RECTANGLE, ELLIPSE, POLYGON, MOVE }

    private Mode currentMode = Mode.FREEHAND;
    private Color currentColor = Color.BLACK;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Stack<Object> undoStack = new Stack<>();
    private Stack<Object> redoStack = new Stack<>();
    private Point startPoint = null;
    private Point endPoint = null;
    private Shape selectedShape = null;  // Track selected shape for deletion
    private List<Point> currentFreehandPoints = new ArrayList<>(); // Used for freehand drawing

    // Clipboard to store cut/copy shapes for paste
    private Shape clipboard = null;
    private boolean isMoving = false;  // Flag to indicate if a shape is currently being moved

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
        JButton polygonButton = new JButton("Polygon");
        JButton moveButton = new JButton("Move");
        JButton colorButton = new JButton("Color");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton cutButton = new JButton("Delete");
        JButton copyButton = new JButton("Copy");
        JButton pasteButton = new JButton("Paste");

        toolbar.add(freehandButton);
        toolbar.add(lineButton);
        toolbar.add(rectangleButton);
        toolbar.add(ellipseButton);
        toolbar.add(polygonButton);
        toolbar.add(moveButton);
        toolbar.add(colorButton);
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.add(cutButton);
        toolbar.add(copyButton);
        toolbar.add(pasteButton);
        add(toolbar, BorderLayout.NORTH);

        // Button actions
        freehandButton.addActionListener(e -> currentMode = Mode.FREEHAND);
        lineButton.addActionListener(e -> currentMode = Mode.LINE);
        rectangleButton.addActionListener(e -> currentMode = Mode.RECTANGLE);
        ellipseButton.addActionListener(e -> currentMode = Mode.ELLIPSE);
        polygonButton.addActionListener(e -> currentMode = Mode.POLYGON);
        moveButton.addActionListener(e -> currentMode = Mode.MOVE);
        colorButton.addActionListener(e -> {
            currentColor = JColorChooser.showDialog(this, "Choose Color", currentColor);
        });
        undoButton.addActionListener(e -> undo());
        redoButton.addActionListener(e -> redo());
        cutButton.addActionListener(e -> deleteSelectedShape());
        copyButton.addActionListener(e -> copyShape());
        pasteButton.addActionListener(e -> pasteShape());

        setVisible(true);
    }

    // Functionality for deleting the selected shape
    private void deleteSelectedShape() {
        if (selectedShape != null) {
            undoStack.push(new UndoableDeleteAction(selectedShape));
            shapes.remove(selectedShape);
            redoStack.clear();
            selectedShape = null;
            repaint();
        }
    }

    // Functionality for copying the selected shape
 // Functionality for copying the selected shape
    private void copyShape() {
        if (selectedShape != null) {
            clipboard = createShapeCopy(selectedShape);  // Store a copy of the selected shape
        }
    }

    // Functionality for pasting the copied shape
    private void pasteShape() {
        if (clipboard != null) {
            // Create a new copy of the shape with a slight offset (e.g., +20 pixels)
            Shape pastedShape = createShapeCopy(clipboard);
            pastedShape.move(20, 20);  // Move it slightly from the original
            shapes.add(pastedShape);
            undoStack.push(pastedShape);
            redoStack.clear();
            repaint();
        }
    }

    // Create a copy of the shape (without modifying position)
    private Shape createShapeCopy(Shape shape) {
        if (shape instanceof LineShape) {
            LineShape line = (LineShape) shape;
            return new LineShape(new Point(line.getX(), line.getY()),
                               new Point(line.getEndX(), line.getEndY()), line.getColor());
        } else if (shape instanceof RectangleShape) {
            RectangleShape rect = (RectangleShape) shape;
            return new RectangleShape(new Point(rect.getX(), rect.getY()),
                                    new Point(rect.getEndX(), rect.getEndY()),
                                    rect.getColor());
        } else if (shape instanceof EllipseShape) {
            EllipseShape ellipse = (EllipseShape) shape;
            return new EllipseShape(new Point(ellipse.getX(), ellipse.getY()),
                                  new Point(ellipse.getEndX(), ellipse.getEndY()),
                                  ellipse.getColor());
        }else if (shape instanceof FreehandShape) {
            FreehandShape freehand = (FreehandShape) shape;
            // Deep copy all points
            List<Point> newPoints = new ArrayList<>();
            for (Point p : freehand.getPoints()) {
                newPoints.add(new Point(p));
            }
            return new FreehandShape(newPoints, freehand.getColor());
        }
        return shape;  // Default return (for other shapes)
    }

    // Move the selected shape during dragging
    private void moveShape() {
        if (selectedShape != null && isMoving) {
            selectedShape.move(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
            startPoint = endPoint;  // Update the start point after movement
            repaint();  // Continuously repaint the shape as it's moved
        }
    }

    // Undo the last action
    private void undo() {
        if (!undoStack.isEmpty()) {
            Object lastAction = undoStack.pop();
            if (lastAction instanceof Shape) {
                Shape shape = (Shape) lastAction;
                shapes.remove(shape);
                redoStack.push(shape);
            }
            repaint();
        }
    }

    // Redo the last undone action
    private void redo() {
        if (!redoStack.isEmpty()) {
            Object lastAction = redoStack.pop();
            if (lastAction instanceof Shape) {
                Shape shape = (Shape) lastAction;
                shapes.add(shape);
                undoStack.push(shape);
            }
            repaint();
        }
    }

    // Canvas for drawing the shapes
    private class Canvas extends JComponent {
        public Canvas() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();  // Save the starting point when mouse is pressed
                    currentFreehandPoints.clear(); // Clear previous points for freehand drawing

                    selectedShape = null;

                    // Check if a shape is clicked for selection
                    for (Shape shape : shapes) {
                        if (shape.contains(startPoint)) {
                            selectedShape = shape;
                            break;
                        }
                    }

                    // If MOVE mode and a shape is selected, start the moving operation
                    if (currentMode == Mode.MOVE && selectedShape != null) {
                        clipboard = selectedShape; // Copy the selected shape into the clipboard
                        isMoving = true; // Start moving the shape
                    }

                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    if (startPoint == null) return;  // Ensure startPoint is set before drawing

                    endPoint = e.getPoint();  // Update the end point when mouse is released
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
                        case POLYGON:
                            Polygon polygon = new Polygon();
                            polygon.addPoint(startPoint.x, startPoint.y);
                            polygon.addPoint(endPoint.x, startPoint.y);
                            polygon.addPoint(endPoint.x, endPoint.y);
                            polygon.addPoint(startPoint.x, endPoint.y);
                            shape = new PolygonShape(polygon, currentColor);
                            break;
                        case MOVE:
                            // If a shape is being moved, update its position and stop moving
                            if (isMoving && selectedShape != null) {
                                selectedShape.move(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
                                isMoving = false; // Stop the move operation after it's completed
                            }
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
                    if (startPoint == null) return;  // Ensure startPoint is set before drawing

                    endPoint = e.getPoint();  // Update end point as the mouse is dragged

                    if (currentMode == Mode.FREEHAND) {
                        currentFreehandPoints.add(endPoint); // Add points to the freehand path
                    }

                    if (currentMode == Mode.MOVE && isMoving && selectedShape != null) {
                        // Move the selected shape while dragging
                        selectedShape.move(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
                        startPoint = endPoint; // Update start point for further movement
                    }

                    repaint();  // Continuously repaint to show the drawing
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                shape.draw(g);  // Draw each shape on the canvas
            }

            // Draw the temporary shape (in real-time) while user is drawing
            if (currentMode == Mode.FREEHAND && !currentFreehandPoints.isEmpty()) {
                g.setColor(currentColor);
                for (int i = 1; i < currentFreehandPoints.size(); i++) {
                    Point p1 = currentFreehandPoints.get(i - 1);
                    Point p2 = currentFreehandPoints.get(i);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            if (currentMode == Mode.LINE && startPoint != null && endPoint != null) {
                g.setColor(currentColor);
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            }

            if (currentMode == Mode.RECTANGLE && startPoint != null && endPoint != null) {
                g.setColor(currentColor);
                g.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            }

            if (currentMode == Mode.ELLIPSE && startPoint != null && endPoint != null) {
                g.setColor(currentColor);
                g.drawOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            }

            if (currentMode == Mode.POLYGON && startPoint != null && endPoint != null) {
                g.setColor(currentColor);
                g.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SketchPad::new);
    }
}

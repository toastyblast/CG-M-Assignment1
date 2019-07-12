import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Java class that when run will show my solution for Portfolio 3 (optional) Exercise 4 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P3E4 {
    private static Graphics2D offscreen;

    public static void main(String[] args) {
        new P3E4().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.0075);

        offscreen = StdDraw.offscreen;

        // NOTE: All the methods called here were made by Yoran Kerbusch and can be found in StdDraw.java at the
        //  bottom of that file.
        // Running this P3E4.java file will show the different types of interactions 2D shapes can have.
        drawUnion(75, 425);
        drawIntersection(75, 125, true);
        drawDifference(375, 425, true);
        drawExclusiveOr(375, 125);
    }

    /**
     * Method that draws a circle and a rectangle, without doing anything special (causing the shapes to merge).
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Constructing Complex Shapes from Geometry Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html [Accessed 7 Feb. 2019].
     *
     * @param x double is the x coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param y double is the y coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     */
    private static void drawUnion(double x, double y) {
        // Code that all these methods use, that draws an intersecting circle and rectangle.
        Area[] shapes = defineCircleAndRectangle(x, y, false);

        // For readability, take the shapes and store them in variables.
        Area circle = shapes[0];
        Area rectangle = shapes[1];

        // Draw both shapes normally, as we just overlay them. Even if the two shapes have no overlap, this will now
        //  add shape 2 to the area of shape 1, and will draw them both on call of "shape1.fill()" or "shape1.draw()".
        offscreen.fill(circle);
        offscreen.fill(rectangle);

        // Finally, draw these area actions we defined.
        StdDraw.draw();
    }

    /**
     * Method that draws a circle, but only draws the part of the circle that's NOT overlapped by the rectangle.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Constructing Complex Shapes from Geometry Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html [Accessed 7 Feb. 2019].
     *
     * @param x double is the x coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param y double is the y coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param showOutlines boolean dictates weather or not to draw a dashed, grey line around the invisible (cut out) shapes.
     */
    private static void drawDifference(double x, double y, boolean showOutlines) {
        Area[] shapes = defineCircleAndRectangle(x, y, showOutlines);

        Area circle = shapes[0];
        Area rectangle = shapes[1];

        // Take out the space the rectangle would overlap over the circle, from the circle. If there is no overlap,
        //  this will only draw shape 1, since shape 2 cannot be subtracted in this area.
        circle.subtract(rectangle);
        // Draw what is left of the circle on the canvas.
        offscreen.fill(circle);

        StdDraw.draw();
    }

    /**
     * Method that draws only the area where the circle and rectangle overlap, and leaves out the rest of these shapes.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Constructing Complex Shapes from Geometry Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html [Accessed 7 Feb. 2019].
     *
     * @param x double is the x coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param y double is the y coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param showOutlines boolean dictates weather or not to draw a dashed, grey line around the invisible (cut out) shapes.
     */
    private static void drawIntersection(double x, double y, boolean showOutlines) {
        Area[] shapes = defineCircleAndRectangle(x, y, showOutlines);

        Area circle = shapes[0];
        Area rectangle = shapes[1];

        // Set the circle area to only draw where it intersects with the rectangle. If there is no overlap between
        //  the shapes, it will not draw either of the shapes upon calling "shape1.draw()" or "shape1.fill()".
        circle.intersect(rectangle);
        // Then fill-draw the circle, as that will show us what is intersecting.
        offscreen.fill(circle);

        StdDraw.draw();
    }

    /**
     * Method that draws a circle and a rectangle, but leaves out the overlap these two shapes have, if any.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Constructing Complex Shapes from Geometry Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html [Accessed 7 Feb. 2019].
     *
     * @param x double is the x coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     * @param y double is the y coordinate of the origin of the two shapes. The circle will be centered on this. It will be the top-left of the rectangle.
     */
    private static void drawExclusiveOr(double x, double y) {
        Area[] shapes = defineCircleAndRectangle(x, y, false);

        Area circle = shapes[0];
        Area rectangle = shapes[1];

        // Add the shape of the rectangle to the circle, but subtract the overlap the two shapes have. If there is no
        //  overlap, this will just act like "shape1.add(shape2)".
        circle.exclusiveOr(rectangle);
        // Draw the full circle that now also includes the rectangle and possible overlap (if there is any).
        offscreen.fill(circle);

        StdDraw.draw();
    }

    /**
     * Helper method that defines two areas: a circle and a rectangle. These overlap and their origin comes from the
     *  given x & y. Outlines will be drawn if the user decides they want that.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Constructing Complex Shapes from Geometry Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html [Accessed 7 Feb. 2019].
     *
     * @param x double x coordinate Origin of the two shapes. Must be between 0 and the set canvas width.
     * @param y double y coordinate Origin of the two shapes. Must be between 0 and the set canvas height.
     * @param showOutlines boolean decides if to draw outlines around the two shapes, for visibility.
     * @return Area[] array with the two areas of the intersecting circle and rectangle shapes.
     * @author Yoran Kerbusch (24143341)
     */
    private static Area[] defineCircleAndRectangle(double x, double y, boolean showOutlines) {
        // Set the given X and Y to the scale of the set canvas size.
        double xs = StdDraw.scaleX(x);
        double ys = StdDraw.scaleY(y);

        // Establish the shapes we will want to draw.
        Ellipse2D.Double circle = new Ellipse2D.Double();
        Rectangle2D.Double rectangle = new Rectangle2D.Double();

        // First, set where the circle will be drawn. WARNING: Java2D draws from TOP-LEFT!!!
        circle.setFrame(xs - 50, ys - 50, 100.0, 100.0);
        Area circ1 = new Area(circle);

        // Also define what area the rectangle will take. Once again, the origin is the TOP-LEFT!!!
        rectangle.setFrame(xs, ys, 80.0, 100.0);
        Area rect1 = new Area(rectangle);

        Area[] shapes = {circ1, rect1};
        if (showOutlines) drawOutlines(shapes);

        return shapes;
    }

    /**
     * Helper method that draws outlines around the Area shapes in the given Area[] array.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *
     * @param shapes Area[] array with Area objects that represent shapes to draw an outline around.
     */
    private static void drawOutlines(Area[] shapes) {
        // Save the current pen stroke before drawing the dashed lines.
        Stroke previousStroke = offscreen.getStroke();
        Color previousPenColor = StdDraw.getPenColor();

        // Create a stroke that is dashed and in the colour gray, to distinguish the shapes.
        float scaledPenRadius = (float) (StdDraw.getPenRadius() * StdDraw.DEFAULT_SIZE);
        offscreen.setStroke(new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, (float) (scaledPenRadius / 2.0), new float[]{5, 15}, 0.0f));
        offscreen.setColor(Color.GRAY);

        // Draw the outlines of the shapes to make them visible.
        for (Area shape : shapes) { offscreen.draw(shape); }

        // Set the pen radius back to what it was.
        offscreen.setStroke(previousStroke);
        offscreen.setColor(previousPenColor);
    }
}

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Java class that when run will show my solution for Portfolio 3 (optional) Exercise 5 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P3E5 {
    private static Graphics2D offscreen;

    public static void main(String[] args) {
        new P3E5().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.02);

        offscreen = StdDraw.offscreen;

        // Draw the olympic rings. Optimized to be used with ringRadius = 100. Increasing this number may cause
        //  imperfections. Making this number smaller will cause clipping.
        // Check at the bottom of the "StdDraw.java" file for the method's code and its helper methods.
        //
        // Consulted source for the methods:
        //  - Eels, H. (2016). How to interlock shapes in Java. [online] Stack Overflow. Available at: https://stackoverflow.com/questions/35382694/how-to-interlock-shapes-in-java [Accessed 7 Feb. 2019].
        drawOlympicRings(75, 325, 100);
    }

    /**
     * Method that draws the five olympic rings with the correct interlocking, using Java2D. Made to be resizable and
     *  work with ringRadius values of 100 or more.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Eels, H. (2016). How to interlock shapes in Java. [online] Stack Overflow. Available at: https://stackoverflow.com/questions/35382694/how-to-interlock-shapes-in-java [Accessed 7 Feb. 2019].
     *
     * @param x double x coordinate of the origin of the first of the five rings, drawing from (top) left to right.
     * @param y double y coordinate of the origin of the first of the five rings, drawing from (top) left to right.
     * @param ringRadius double is the size of the ring. Works best with ringRadius = 100. Should not be smaller. Higher values may cause slight imperfections.
     */
    private static void drawOlympicRings(double x, double y, double ringRadius) {
        // Calculate the distance between each ring left and down-/upwards.
        double changeX = ringRadius / 1.6;
        double changeY = ringRadius / 2.0;

        double currX = x;
        double currY = y;
        // Draw the first ring from left: the blue ring.
        drawRing(StdDraw.scaleX(currX), StdDraw.scaleY(currY), Color.BLUE, ringRadius);
        // Increment left and down to do the next ring.
        currX += changeX;
        currY -= changeY;
        // Draw the second ring from the left: the orange (IRL it's gold) ring (so that it overlaps the intersections with blue).
        drawRing(StdDraw.scaleX(currX), StdDraw.scaleY(currY), Color.ORANGE, ringRadius);
        // Also draw the overlap the blue ring has over the orange ring on their top intersection.
        drawCoverArc(StdDraw.scaleX(currX - changeX), StdDraw.scaleY(currY + changeY), Color.BLUE, ringRadius, 330, 60);
        // Increment left and up to do the next ring.
        currX += changeX;
        currY += changeY;
        // Draw the third ring from the left: the black ring (so that it overlaps the intersections with orange).
        drawRing(StdDraw.scaleX(currX), StdDraw.scaleY(currY), Color.BLACK, ringRadius);
        // Also draw the overlap the orange ring has over the black ring on their top intersection.
        drawCoverArc(StdDraw.scaleX(currX - changeX), StdDraw.scaleY(currY - changeY), Color.ORANGE, ringRadius, 54, 30);
        // Increment left and down to do the next ring.
        currX += changeX;
        currY -= changeY;
        // Draw the fourth ring from the left: the green ring (so that it overlaps the intersections with black).
        drawRing(StdDraw.scaleX(currX), StdDraw.scaleY(currY), Color.GREEN.darker(), ringRadius);
        // Also draw the overlap the black ring has over the green ring on their left bottom intersection.
        drawCoverArc(StdDraw.scaleX(currX - changeX), StdDraw.scaleY(currY + changeY), Color.BLACK, ringRadius, 330, 60);
        // Increment left and up to do the next ring.
        currX += changeX;
        currY += changeY;
        // Draw the fifth & final ring from the left: the red ring (so that it overlaps the intersections with green).
        drawRing(StdDraw.scaleX(currX), StdDraw.scaleY(currY), Color.RED, ringRadius);
        // Finally also draw the overlap the green ring has over the red ring on their bottom intersection.
        drawCoverArc(StdDraw.scaleX(currX- changeX), StdDraw.scaleY(currY - changeY), Color.GREEN.darker(), ringRadius, 54, 30);

        StdDraw.draw();
    }

    /**
     * Helper method that draws an arc starting and ending between two given angles. Arc consists of two lines, the
     *  coloured line and the under, white line. This method assumes the given x & y values have already been scaled
     *  with the canvas.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Eels, H. (2016). How to interlock shapes in Java. [online] Stack Overflow. Available at: https://stackoverflow.com/questions/35382694/how-to-interlock-shapes-in-java [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *
     * @param x double x coordinate of the origin of the arc, drawing from its tallest, left-most point.
     * @param y double y coordinate of the origin of the arc, drawing from its tallest, left-most point.
     * @param color Color color the arc must have. The outer line will always be white.
     * @param ringRadius double double is the full width & length the ring of the arc must be. Assumes that this is in scale with the canvas.
     * @param start double angle (in degrees) at which the arc starts on the invisible circle it draws on.
     * @param end double angle (in degrees) at which the arc ends on the invisible circle it draws on.
     */
    private static void drawCoverArc(double x, double y, Color color, double ringRadius, double start, double end) {
        Arc2D.Double arc = new Arc2D.Double();
        float scaledPenRadius = (float) (StdDraw.getPenRadius() * StdDraw.DEFAULT_SIZE);

        // Draw the underline of the coloured arc, so that a bigger, white outline is created.
        offscreen.setStroke(new BasicStroke((scaledPenRadius * 1.4f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        offscreen.setColor(Color.WHITE);
        arc.setArc(x, y, ringRadius, ringRadius, start, end, Arc2D.OPEN);
        offscreen.draw(arc);

        // Then draw the actual normal-sized, coloured arc over that, creating an arc with an outline.
        offscreen.setStroke(new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        offscreen.setColor(color);
        arc.setArc(x, y, ringRadius, ringRadius, start, end, Arc2D.OPEN);
        offscreen.draw(arc);
    }

    /**
     * Helper method that draws a ring on top of a bigger white right, to form an outlined, unfilled circle in the
     *  given colour. This method assumes the given x & y values have already been scaled with the canvas.
     * @author Yoran Kerbusch (24143341)
     * Consulted sources:
     *  - Eels, H. (2016). How to interlock shapes in Java. [online] Stack Overflow. Available at: https://stackoverflow.com/questions/35382694/how-to-interlock-shapes-in-java [Accessed 7 Feb. 2019].
     *  - Docs.oracle.com. (n.d.). Drawing Geometric Primitives (Java2D). [online] Available at: https://docs.oracle.com/javase/tutorial/2d/geometry/primitives.html [Accessed 7 Feb. 2019].
     *
     * @param x double x coordinate of the origin of the ring, drawing from (top) left to right.
     * @param y double y coordinate of the origin of the ring, drawing from (top) left to right.
     * @param color Color color the inner circle of the ring must have. The outer line will always be white.
     * @param ringRadius double is the full width & length the ring must be. Assumes that this is in scale with the canvas.
     */
    private static void drawRing(double x, double y, Color color, double ringRadius) {
        Ellipse2D.Double circle = new Ellipse2D.Double();
        float scaledPenRadius = (float) (StdDraw.getPenRadius() * StdDraw.DEFAULT_SIZE);

        // Draw the bigger outline (the white line) of the circle, on which the normal sized, coloured ring will be placed.
        offscreen.setStroke(new BasicStroke((scaledPenRadius * 1.4f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        offscreen.setColor(Color.WHITE);
        circle.setFrame(x, y, ringRadius, ringRadius);
        offscreen.draw(new Area(circle));

        // Then draw the normal sized, coloured line on top of the white line, creating a ring with white outlines.
        offscreen.setStroke(new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        offscreen.setColor(color);
        circle.setFrame(x, y, ringRadius, ringRadius);
        offscreen.draw(new Area(circle));
    }
}

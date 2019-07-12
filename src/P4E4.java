import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Java class that when run will show my solution for Portfolio 4 Exercise 4 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P4E4 {
    private static Graphics2D offscreen;

    // Variables specific to the clock.
    private static int clockBorderBuffer = 5; // The width taken up by the border of the clock.
    private static int shortTickLength = 10;  // Length of a tick that represents a minute ~ 10 is for radius 200.
    private static int mediumTickLength = 20;  // Length of a tick that represents every 5th minute ~ 20 is for radius 200.
    private static int longTickLength = 35; // Length of a tick that represents every 15th minute ~ 20 is for radius 200.

    public static void main(String[] args) {
        new P4E4().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.002);

        offscreen = StdDraw.offscreen;

        // This call draws the clock. This will still use some StdDraw methods like circle() and filledCircle() purely
        //  for convenience and to slim down this already complicated code.
        drawAnalogClock(250.0, 250.0, 200.0);
    }

    /**
     * Method that draws an analog clock face with arms that will rotate in real time. The minute and hour arms are
     *  dynamic and will thus change slightly with every second and minute passed.
     * The clock will also make its elements smaller if the radius is made smaller, however, it is optimized to work at a radius of 200.0
     * @author Yoran Kerbusch (24143341) and S. Hannah for the code this was based on.
     * Consulted sources:
     *  - Hannah, S. (2015). Graphics Part 2: Drawing an Analog Clock. [online] Codenameone.com. Available at: http://www.codenameone.com/blog/codename-one-graphics-part-2-drawing-an-analog-clock.html [Accessed 13 Feb. 2019].
     *
     * @param centerX double is the x-coordinate of the center of the clock.
     * @param centerY double is the y-coordinate of the center of the clock.
     * @param radius double is half the width the clock must have, including the back face of it.
     */
    private static void drawAnalogClock(double centerX, double centerY, double radius) {
        float scaledPenRadius = (float)(StdDraw.getPenRadius() * StdDraw.DEFAULT_SIZE);
        double xs = StdDraw.scaleX(centerX);
        double ys = StdDraw.scaleY(centerY);

        // Adjust all size values to the size of the clock. Optimized for 200+ pixel sizes.
        clockBorderBuffer = (int)(radius / 40.0);
        shortTickLength = (int)(radius / 20.0);
        mediumTickLength = (int)(radius / 10.0);
        longTickLength = (int)(radius / 5.0);

        // Draw a filled circle background for the clock, to give it some contrast.
        offscreen.setColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.filledCircle(centerX, centerY, radius);

        // Draw a circle surrounding the clock, creating a sort of border for it.
        offscreen.setColor(StdDraw.BOOK_BLUE);
        offscreen.setStroke(new BasicStroke((scaledPenRadius * clockBorderBuffer), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        StdDraw.circle(centerX, centerY, radius);

        // Once the back of the clock has been drawn, draw the second/minute/hour ticks of the clock.
        drawClockTicks(xs, ys, radius, scaledPenRadius);

        // To complete the clock background, draw the numbers of the clock.
        drawClockNumbers(xs, ys, radius);

        // Save the drawing of the clock background, so we don't have to redraw it each time like the clock.
        BufferedImage clockBackground = deepCopy(StdDraw.offscreenImage);
        // Get the original transform of offscreen, to revert after each rotate (otherwise all shapes will turn further and further)
        AffineTransform originalTransform = offscreen.getTransform();

        // Draw the seconds arm, having it pointed from the origin, as we're immediately going to rotate it anyways.
        Line2D.Double secsArm = new Line2D.Double(xs, ys, xs, (ys - (radius - mediumTickLength)));

        // Draw the minute hand, which is a long triangle (made of a filled polygon drawn using generalPath), pointing up to the short ticks.
        float[] minXs = new float[]{(float)xs, (float)(xs + clockBorderBuffer), (float)(xs + (clockBorderBuffer / 2f)), (float)(xs - clockBorderBuffer)};
        float[] minYs = new float[]{(float)(ys), (float)(ys), (float)(ys - (radius - shortTickLength - clockBorderBuffer)), (float)(ys)};
        GeneralPath minsArm = drawPolygon(minXs, minYs);

        // And draw the hour arm in a similar fashion to the minutes one, only less wide and long.
        float[] hourXs = new float[]{(float)(xs), (float)(xs + (clockBorderBuffer / 1.5f)), (float)(xs + (clockBorderBuffer / 3f)), (float)(xs - (clockBorderBuffer / 1.5f))};
        float[] hourYs = new float[]{(float)(ys), (float)(ys), (float)(ys - (radius - longTickLength - clockBorderBuffer) * 0.85), (float)(ys)};
        GeneralPath hoursArm = drawPolygon(hourXs, hourYs);

        while(true) {
            Calendar present = Calendar.getInstance(TimeZone.getDefault());

            // Calculate the angle to which we need to turn the seconds arm to represent the current time's seconds.
            double seconds = (double)(present.get(Calendar.SECOND));
            double secondAngle = seconds / 60.0 * 2.0 * Math.PI;
            // Rotate the seconds arm to show the current amount of seconds we're at.
            offscreen.rotate((float)(secondAngle), (int)(xs), (int)(ys));
            // And then actually draw the seconds arm in the current rotation.
            offscreen.setColor(Color.RED);
            offscreen.setStroke(new BasicStroke((scaledPenRadius * 2f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            offscreen.draw(secsArm);
            // Before finally returning offscreen to the original rotation.
            offscreen.setTransform(originalTransform);

            // Just like with the seconds, calculate the angle for the current amount of minutes for the minute arm.
            double minutes = (double)(present.get(Calendar.MINUTE)) + seconds / 60.0;
            double minuteAngle = minutes / 60.0 * 2.0 * Math.PI;
            offscreen.rotate((float)(minuteAngle), (int)(xs), (int)(ys));
            offscreen.setColor(Color.BLACK);
            offscreen.fill(minsArm);
            offscreen.setTransform(originalTransform);

            // For hours, we have to take into consideration the amount of minutes that have passed as well, so that the
            //  hours arm is somewhere between the current and next hour, instead of at one or the other.
            double hours = (double)(present.get(Calendar.HOUR)) + (double)(present.get(Calendar.MINUTE)) / 60.0;
            // And instead of doing by 60, we do by 12, as there are 12 hours on an analog clock.
            double hourAngle = hours / 12.0 * 2.0 * Math.PI;
            // And draw the hours arm just like we did with the minutes one.
            offscreen.rotate((float)(hourAngle), (int)(xs), (int)(ys));
            offscreen.fill(hoursArm);
            offscreen.setTransform(originalTransform);

            // Finally, draw a small circle in the middle of the clock to cover the ends of the arms, just for aesthetics.
            StdDraw.filledCircle(xs, ys, (clockBorderBuffer * 1.4));

            StdDraw.show(1000);

            System.out.println("Hour: " + hours + "\nMinute: " + minutes + "\nSecond: " + seconds + "\n");

            StdDraw.clear();
            offscreen.drawImage(clockBackground, 0, 0, null);
        }
    }

    /**
     * Helper method that draws the ticks on the background of the clock, representing the seconds/minutes.
     * @author Yoran Kerbusch (24143341) and S. Hannah for the code this was based on.
     * Consulted sources:
     *  - Hannah, S. (2015). Graphics Part 2: Drawing an Analog Clock. [online] Codenameone.com. Available at: http://www.codenameone.com/blog/codename-one-graphics-part-2-drawing-an-analog-clock.html [Accessed 13 Feb. 2019].
     *
     * @param centerX double is the x-coordinate of the center of the clock.
     * @param centerY double is the y-coordinate of the center of the clock.
     * @param radius double is half the width the clock must have, including the back face of it.
     * @param penRadius double is the thickness of the pen, so we know how big to make the ticks (adaptive to the size of the clock)
     */
    private static void drawClockTicks(double centerX, double centerY, double radius, float penRadius) {
        // Define strokes for each size of tick, to make them distinctive.
        BasicStroke secondStroke = new BasicStroke((penRadius * 2.5f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        BasicStroke fiveStroke = new BasicStroke((penRadius * 4.0f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        BasicStroke quarterStroke = new BasicStroke((penRadius * 5.5f), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        Stroke originalStroke = offscreen.getStroke();

        // Draw a tick for every second/minute on the clock.
        for (int i = 1; i <= 60; i++) {
            offscreen.setColor(Color.GRAY);
            offscreen.setStroke(secondStroke);
            // We assume that most of the time, it's a small tick.
            int length = shortTickLength;

            if (i % 15 == 0) {
                // Every 15th tick is one for an hour, so it's the biggest.
                offscreen.setColor(Color.DARK_GRAY);
                offscreen.setStroke(quarterStroke);
                length = longTickLength;
            } else if (i % 5 == 0) {
                // Otherwise, every 5th tick is inbetween the big and small ticks.
                offscreen.setStroke(fiveStroke);
                length = mediumTickLength;
            }

            // Calculate what two (x,y) coordinates are needed to create a tick at the current angle and distance from the center of the clock.
            double[] coords = calculateFromRadius(centerX, centerY, ((double)(i) / 60.0), (radius - length - clockBorderBuffer), (radius - clockBorderBuffer));
            Line2D.Double tick = new Line2D.Double(coords[0], coords[1], coords[2], coords[3]);
            offscreen.draw(tick);
        }

        // Reset all the strokes and colours we have used back to the originals.
        offscreen.setColor(Color.BLACK);
        offscreen.setStroke(originalStroke);
    }

    /**
     * Method that draws at from two distances, at a certain angle of a circle from a center origin a stripe, meant to be used for ticks on a clock.
     * @author Yoran Kerbusch (24143341) and F. Swartz for the original code this was based on.
     *
     * Consulted sources:
     *  - Swartz, F. (2013). Analog Clock - Java. [online] Johnloomis.org. Available at: http://www.johnloomis.org/ece538/notes/2013/day09/clock/clock.html [Accessed 13 Feb. 2019].
     *  - Hannah, S. (2015). Graphics Part 2: Drawing an Analog Clock. [online] Codenameone.com. Available at: http://www.codenameone.com/blog/codename-one-graphics-part-2-drawing-an-analog-clock.html [Accessed 13 Feb. 2019].
     *
     * @param x double should be the x-coordinate of the center of a circle we are calculating the points of the line for.
     * @param y double should be the y-coordinate of the center of a circle we are calculating the points of the line for.
     * @param section double is the angle section of the circle at which we are drawing this line.
     * @param start double is the distance from the center at which the line we are calculating the points of should end.
     * @param end double is the distance from the center at which the line we are calculating the points of should end.
     */
    private static double[] calculateFromRadius(double x, double y, double section, double start, double end) {
        // RADIANS from 12 o'clock would be the calculation "from12 = section * (2.0 * Math.PI)"
        // This would then for 3 o'clock (as this is easier to work with in calculations with Java2D, as this will then make it from the zero point) be "from3 = (Math.PI / 2.0) - from12"
        // So that is "from3 = Math.PI / 2.0 - (location * (2.0 * Math.PI))" for the complete formula.
        double radiansFromThree = Math.PI / 2.0 - (section * 2.0 * Math.PI);
        // Since we're using both the sine and cosine twice for the outer and inner points of each tick, we do the calculations once and store them.
        double fromThreeSin = Math.sin(radiansFromThree);
        double fromThreeCos = Math.cos(radiansFromThree);

        // Create the (x,y) for the start of the tick (inner circle), so the closest point to the center the tick will be.
        int innerX = (int)(x + (start * fromThreeSin));
        int innerY = (int)(y + (start * fromThreeCos));
        // Create the (x,y) for the end of the tick (outer circle), which is just a bit of a distance from the outer ring of the clock.
        int outerX = (int)(x + (end * fromThreeSin));
        int outerY = (int)(y + (end * fromThreeCos));

        // Then finally draw the line.
        return new double[]{innerX, innerY, outerX, outerY};
    }

    /**
     * Method that is a simplified version of drawCharacters(...). Draws numbers 1 to 12 with 12 being at the top, in a ring.
     * @author Yoran Kerbusch (24143341)
     *
     * @param centerX double is the x-coordinate of the center of the clock.
     * @param centerY double is the y-coordinate of the center of the clock.
     * @param radius double is half the width the clock must have, including the back face of it.
     */
    private static void drawClockNumbers(double centerX, double centerY, double radius) {
        Font original = offscreen.getFont();
        offscreen.setFont(new Font("TimesRoman", Font.BOLD, 18));

        double degreesPerPoint = (2 * Math.PI) / 12;
        for (int i = 1; i <= 12; i++) {
            // Do the i - 3 % 12 to create a loop from 1 to 12, where we count 12 as actually being on the position of 9, so that 12 will be drawn on top correctly.
            double pointX = (centerX - (radius * 0.03)) + (radius * 0.73) * Math.cos(((i - 3) % 12) * degreesPerPoint);
            double pointY = (centerY + (radius * 0.04)) + (radius * 0.72) * Math.sin(((i - 3) % 12) * degreesPerPoint);

            offscreen.drawString("" + i, (int)(pointX), (int)(pointY));
        }
        // Set the font back to the original, if it's going to be used further.
        offscreen.setFont(original);
    }

    /**
     * Helper method that creates a polygon from two arrays with x and y coordinates.
     * @author Yoran Kerbusch (24143341)
     *
     * @param xPoints are all the x coordinates at which the polygon must draw a point and connect lines between.
     * @param yPoints are all the y coordinates at which the polygon must draw a point and connect lines between.
     * @return GeneralPath is the polygon object that the calling system can then draw.
     */
    private static GeneralPath drawPolygon(float[] xPoints, float[] yPoints) {
        // GeneralPath is a line consisting of possibly multiple points, all connected to each other (except for the first and last, unless desired as in this method).
        GeneralPath polygon = new GeneralPath();

        // Move the polygon's starting point to the first (x,y) in the array.
        polygon.moveTo(xPoints[0], yPoints[0]);

        for (int i = 1; i < xPoints.length; i++) {
            // For every extra (x,y) in the arrays, draw the point towards that next point.
            polygon.lineTo(xPoints[i], yPoints[i]);
        }

        // Once all points have been drawn with lines, connect the first and last points with a line as well.
        polygon.closePath();

        return polygon;
    }

    /**
     * Method that makes a deep copy of a given BufferedImage object.
     * @author Klark (https://stackoverflow.com/a/3514297/8773356), all rights go to this person.
     *
     * @param bi BufferedImage the BufferedImage to make a deep copy of.
     * @return BufferedImage a deep copy/clone of the given BufferedImage
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}

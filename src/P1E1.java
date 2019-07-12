import java.awt.*;

/**
 * Run this class to see the result of Exercise 1, which involves just testing with all drawing tools in the StdDraw library.
 * @author Yoran Kerbusch
 *
 * Consulted sources:
 * - Introcs.cs.princeton.edu. (2019). StdDraw. [online] Available at: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html [Accessed 22 Jan. 2019].
 */
public class P1E1 {
    public static void main(String[] args) {
        // Draw separation lines...
        StdDraw.setPenRadius(.002);
        StdDraw.line(0, .8, 1, .8);
        StdDraw.line(0, .6, 1, .6);
        StdDraw.line(0, .4, 1, .4);
        StdDraw.line(0, .2, 1, .2);

        // Write titles for each box...
        StdDraw.text(.04, .975, "Lines");
        StdDraw.text(.08, .775, "Rectangles");
        StdDraw.text(.055, .575, "Ellipses");
        StdDraw.text(.095, .375, "Arcs (Curves)");
        StdDraw.text(.05, .175, "Circles");

        // Draw a few lines...
        // The letter 'H'
        StdDraw.line(.1, .95, .1, .85);
        StdDraw.line(.1, .9, .15, .9);
        StdDraw.line(.15, .95, .15, .85);
        // Dutch Royal orange in RGB values!
        StdDraw.setPenColor(255,79,0);
        // The letter 'I' (in Italics)
        StdDraw.line(.175, .95, .225, .95);
        StdDraw.line(.2, .95, .225, .85);
        StdDraw.line(.2, .85, .25, .85);

        // Draw some rectangles (and squares)...
        // Squares first
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.004);
        StdDraw.square(.475, .675, .025);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(.002);
        StdDraw.square(.525, .725, .05);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(.8, .6875, .075);
        // And then a few rectangles
        StdDraw.rectangle(.25, .7, .175, .05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledRectangle(.15, .675, .05, .07);

        // Draw some ellipses...
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.ellipse(.35, .525, .04, .065);
        StdDraw.filledEllipse(.55, .45, .15, .03);

        // Draw some arcs (curves)...
        StdDraw.setPenRadius(.01);
        StdDraw.arc(.5, .2875, .1, 35, 220);
        StdDraw.setPenRadius(.002);
        StdDraw.arc(.425, .375, .1, 180, 335);

        // Draw some circles...
        StdDraw.circle(.35, .11, .07);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledCircle(.6, .08, .06);

        // Finally, throw in some random polygons
        StdDraw.setPenColor(StdDraw.BLACK);
        double[] x1 = {.75, .8, .85};
        double[] y1 = {.3, .55, .3};
        StdDraw.polygon(x1, y1);
        StdDraw.setPenColor(StdDraw.CYAN);
        double[] x2 = {.6, .9, .7, .95, .85};
        double[] y2 = {.225, .45, .175, .425, .2};
        StdDraw.polygon(x2, y2);
    }
}

/**
 * Run this class to see the result of Exercise 2, which involves having to print a drawn version of "Hello world!" using 2D shapes.
 * @author Yoran Kerbusch
 *
 * Consulted sources:
 * - Introcs.cs.princeton.edu. (2019). StdDraw. [online] Available at: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html [Accessed 22 Jan. 2019].
 * - Docs.oracle.com. (2019). Programming With Assertions. [online] Available at: https://docs.oracle.com/javase/7/docs/technotes/guides/language/assert.html [Accessed 23 Jan. 2019].
 */
public class P1E2 {
    private static double bottomY = 0.25;
    private static double middleY = 0.5;
    private static double topY = 0.75;

    public static void main(String[] args) {
        // Set a custom rules that are better suited for a line of text.
        StdDraw.setCanvasSize(1500, 375);
        StdDraw.setPenRadius(.0125);

        // H
        StdDraw.line(.025, topY, .025, bottomY);
        StdDraw.line(.025, middleY, .05, middleY);
        StdDraw.line(.05, topY, .05, bottomY);
        // E
        StdDraw.line(.0625, topY, .0625, bottomY);
        StdDraw.line(.0625, topY, .0875, topY);
        StdDraw.line(.0625, middleY, .075, middleY);
        StdDraw.line(.0625, bottomY, .0875, bottomY);
        // L
        drawL(.1);
        // L
        drawL(.1375);
        // O
        StdDraw.ellipse(.1875, middleY, .0125, (middleY / 2));

        // W
        StdDraw.line(.2375, topY, .25, bottomY);
        StdDraw.line(.25, bottomY, .2625, middleY);
        StdDraw.line(.2625, middleY, .275, bottomY);
        StdDraw.line(.275, bottomY, .2875, topY);
        // O
        StdDraw.ellipse(.3125, middleY, .0125, (middleY / 2));
        // R
        StdDraw.line(.3375, bottomY, .3375, topY);
        StdDraw.arc(.3375, .625, .125, 270, 90); // The "R" is a bit wider because you cannot set two axis for the arc radius, like an ellipses.
        StdDraw.line(.3375, middleY, .4625, bottomY);
        // L
        drawL(.475);
        // D
        StdDraw.arc(.5125, middleY, .25, 270, 90); // The "D" is a bit wider because you cannot set two axis for the arc radius, like an ellipses.
        StdDraw.line(.5125, bottomY, .5125, topY);
        // !!!
        double[] x = {.775, .8, .7875};
        double[] y = {topY, topY, .35};
        StdDraw.polygon(x, y);
        StdDraw.circle(.7875, .3, .0125);
    }

    /**
     * Helper method to draw the letter "L" on the screen.
     * @param leftBottomX double is the left bottom the "L" will originate from.
     *  Must be a double between and including 0.0 & 1.0
     */
    private static void drawL(double leftBottomX) {
        // Add some assertions as DEBUG checks to verify exceptions being thrown correctly at wrong user input.
        assert leftBottomX >= 0.0 && leftBottomX <= 1.0 : "Please give a valid double between & including 0.0 and 1.0 for the origin of the letter \"L\".";

        StdDraw.line(leftBottomX, topY, leftBottomX, bottomY);
        StdDraw.line(leftBottomX, bottomY, (leftBottomX + .025), bottomY);
    }
}

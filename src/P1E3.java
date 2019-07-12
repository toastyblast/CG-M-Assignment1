/**
 * Run this class to see the result of Exercise 3, which involves having to print a draw a 2D car found in the exercise description using the StdDraw library.
 * @author Yoran Kerbusch
 *
 * Consulted sources:
 * - Introcs.cs.princeton.edu. (2019). StdDraw. [online] Available at: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html [Accessed 23 Jan. 2019].
 * - Docs.oracle.com. (2019). Programming With Assertions. [online] Available at: https://docs.oracle.com/javase/7/docs/technotes/guides/language/assert.html [Accessed 23 Jan. 2019].
 */
public class P1E3 {
    // NOTE: If you want details added to the base car, set the variable below to "true".
    private static boolean addDetails = false;

    public static void main(String[] args) {
        StdDraw.setPenRadius(.0125);

        // Draw the basic car as shown in the exercise's image.
        // Front bumper of the car.
        StdDraw.line(.05, .425, .05, .3);
        StdDraw.line(.05, .3, .15, .3);
        StdDraw.line(.2, .425, .05, .425);
        // Front wheel of the car.
        StdDraw.arc(.2, .3, .05, 180, 360);
        // Underside line of the car
        StdDraw.line(.25, .3, .65, .3);
        // Rear wheel of the car
        StdDraw.arc(.7, .3, .05, 180, 360);
        // Rear bumper of the car.
        StdDraw.line(.75, .3, .85, .3);
        StdDraw.line(.85, .3, .8375, .425);
        // Back windshield of the car.
        StdDraw.arc(.7375, .425, .1, 0, 90);
        // Roof of the car.
        StdDraw.line(.35, .525, .7375, .525);
        // Front windshield of the car.
        StdDraw.line(.2, .425, .35, .525);

        // Add additional details to the base car that was required to be drawn.
        if (addDetails) drawDetails();
    }

    /**
     * Helper method to add (overlay) details to the base car being drawn.
     */
    private static void drawDetails() {
        // Create full wheels, with the protection over it.
        drawWheel(.2, .3);
        drawWheel(.7, .3);

        // Draw the front light, right on the top front of the bumper.
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.arc(.0625, .4125, .025, 270, 360);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.arc(.05, .425, .05, 270, 360);
        // Draw the back light as well, on the same height as the front light.
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(.825, .425, .8125, .375);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(.8375, .425, .8125, .425);
        StdDraw.line(.8125, .425, .8, .375);
        StdDraw.line(.8375, .375, .8, .375);

        // Finally,Draw an antenna just before the curved back windshield.
        StdDraw.line(.7375, .525, .7625, .55);
        StdDraw.setPenRadius(.02);
        StdDraw.point(.7625, .55);
    }

    /**
     * Helper method for the details adder to add more detailed wheels to the car.
     * @param centerX double is the desired center X coordinate of the wheel.
     * @param centerY double is the desired center Y coordinate of the wheel.
     */
    private static void drawWheel(double centerX, double centerY) {
        // Assert that the program/user is giving us valid doubles for the coordinates. Mainly for DEBUG purposes, as asserts should not be in production code.
        assert centerX >= 0.0 && centerX <= 1.0 : "Please give a valid double between & including 0.0 and 1.0 for centerX of a wheel.";
        assert centerY >= 0.0 && centerY <= 1.0 : "Please give a valid double between & including 0.0 and 1.0 for centerY of a wheel.";

        StdDraw.circle(centerX, centerY, .05);
        StdDraw.filledCircle(centerX, centerY, .0125);
        StdDraw.arc(centerX, centerY, .075, 0, 180);
    }
}

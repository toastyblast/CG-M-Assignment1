/**
 * Java class that when run will show my solution for Portfolio 3 Exercises 2 & 3 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P3E2_3 {
    public static void main(String[] args) {
        new P3E2_3().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.02);

        // Portfolio 3 (week 3) - Exercise 2:
//        pointCircle(250, 250, 200, 16);
        // Since the code below uses the same method to draw its circle, this can be left commented out.

        // Portfolio 3 (week 3) - Exercise 3:
        double[][] points = pcExercise3(250, 250, 200, 16);

        // For cosmetic distinction, make the lines thinner and gray.
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GRAY);

        // Draw the random lines between the points.
        drawBetweenPoints(points, 0.25);

        // For cosmetic reasons, draw the dots again, so they're above the lines. Use the code for P3E2 for this.
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        // Use the Exercise 2 code just so the dots overlay on top of the lines.
        pointCircle(250, 250, 200, 16);
    }

    /**
     * Method that draws N amount of points in a circle shape, adhering to a given radius from the given origin.
     * @author Yoran Kerbusch (24143341)
     *
     * @param x int pixel (between 0 & canvas width) that is the center of the circle to be drawn.
     * @param y int pixel (between 0 & canvas height) that is the center of the circle to be drawn.
     * @param radius int amount of pixels from the given origin (x, y) the dots should be placed.
     * @param points int amount of points to draw. Must be 1 or more for correct functionality.
     */
    private void pointCircle(int x, int y, int radius, int points) {
        // Calculate the amount of degrees each point will be at differing from the last.
        double degreesPerPoint = (2 * Math.PI) / points;

        for (int i = 1; i <= points; i++) {
            // Then use that to calculate the (x,y) coordinates for the dot on an imaginary circle.
            double pointX = Math.round(x + radius * Math.cos(i * degreesPerPoint));
            double pointY = Math.round(y + radius * Math.sin(i * degreesPerPoint));

            StdDraw.point(pointX, pointY);
        }
    }

    /**
     * Version of the pointCircle method for Exercise 2 that returns a 2D array of double arrays with the
     *  coordinates of each point placed. Made for exercise 3.
     *  @author Yoran Kerbusch (24143341)
     *
     * @param x int pixel (between 0 & canvas width) that is the center of the circle to be drawn.
     * @param y int pixel (between 0 & canvas height) that is the center of the circle to be drawn.
     * @param radius int amount of pixels from the given origin (x, y) the dots should be placed.
     * @param points int amount of points to draw. Must be 1 or more for correct functionality.
     * @return double[][] array with arrays of each 2 doubles, the first being the point's x, the second the y.
     */
    private double[][] pcExercise3(int x, int y, int radius, int points) {
        double degreesPerPoint = (2 * Math.PI) / points;
        double[][] drawnPoints = new double[points][];

        for (int i = 1; i <= points; i++) {
            double pointX = Math.round(x + radius * Math.cos(i * degreesPerPoint));
            double pointY = Math.round(y + radius * Math.sin(i * degreesPerPoint));

            StdDraw.point(pointX, pointY);
            // Unlike pointCircle() for Exercise 2, we store each (x,y) for the points as well, so we can draw lines
            //  between them later. We do this in an array of arrays so we can keep the coordinates of each point separate.
            drawnPoints[(i - 1)] = new double[] {pointX, pointY};
        }

        // Return the coordinates of each point in the array of arrays.
        return drawnPoints;
    }

    /**
     * Method that randomly draws lines between the given 2D array of points, at a given probability. Made for exercise 3.
     * @author Yoran Kerbusch (24143341)
     *
     * @param points double[][] holds all the points' X & Y coordinates, as an array per point.
     * @param probability double number that decides how high the chance is that lines get randomly drawn. Must be a value from min 0.0 to max 1.0
     */
    private void drawBetweenPoints(double[][] points, double probability) {
        // Go through each point we made to see if we'll draw a line between it and the others.
        for (int i = 0; i < points.length; i++) {
            // Then go through every other point to decide if to draw a line between the current and that one.
            for (int j = 0; j < points.length; j++) {
                // Only draw a line if the point we're looking at isn't the same as current point & if the probability
                //  has been reached on random.
                if (i != j && Math.random() <= probability) {
                    StdDraw.line(points[i][0], points[i][1], points[j][0], points[j][1]);
                }
            }
        }
    }
}

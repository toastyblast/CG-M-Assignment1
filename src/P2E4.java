/**
 * Java class that when run will show my solution for Portfolio 2 Exercise 4 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P2E4 {
    public static void main(String[] args) {
        new P2E4().run();
    }

    private void run() {
        StdDraw.setScale(0, 512);

        // Keep track of the time taken to draw the 3 lines, as P2E1_2 & P2E3 will draw the same lines. This way, you can compare the time taken by each.
        long startTime = System.nanoTime();
        specialDrawLine(10, 10, 40, 30);
        specialDrawLine(10, 10, 40, 90);
        long endTime = System.nanoTime();
        long durationMilliseconds = (endTime - startTime) / 1000000;
        // Output the time taken in seconds, so it can be compared with P2E1_2 & P2E3 if these are run too.
        System.out.println("Time taken in milliseconds by special for two short lines: " + durationMilliseconds);
    }

    /**
     * Helper method to draw a line between two coordinates on the canvas.
     * Assumes that x2 & y2 are of a higher value than x1 & y1 respectively.
     *
     * @param x1 int is the x coordinate of the line's origin.
     * @param y1 int is the y coordinate of the line's origin.
     * @param x2 int is the x coordinate of the line's end (must adhere to x2 < x1).
     * @param y2 int is the y coordinate of the line's end (must adhere to y2 < y1).
     */
    private void specialDrawLine(int x1, int y1, int x2, int y2) {
        // Calculate the differences between the start and end coordinates for the lines.
        int dy = y2 - y1;
        int dx = x2 - x1;
        // Then calculate the slope of the line.
        double m = (double) dy / dx;
        // And then get the distance from the origin (0,0) to the point where the line would intersect the y-axis, if it would go past this.
        double c = y1 - (m * x1);

        for (int i = x1; i < x2; i++) {
            // Draw each of the points according to the special algorithm given in the exercise's pseudo-code.
            double y = (m * i) + c;
            StdDraw.point(i, Math.round(y));
        }
    }
}

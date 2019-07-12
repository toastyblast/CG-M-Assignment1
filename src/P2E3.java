/**
 * Java class that when run will show my solution for Portfolio 2 Exercise 3 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P2E3 {
    public static void main(String[] args) {
        new P2E3().run();
    }

    private void run() {
        StdDraw.setScale(0, 512);

        // Keep track of the time taken to draw the 3 lines, as P2E1_2 & P2E4 will draw the same lines. This way, you can compare the time taken by each.
        long startTime = System.nanoTime();
        incrementDrawLine(0, 500, 10, 0);
        incrementDrawLine(0, 500, 10, .5);
        incrementDrawLine(0, 500, 10, 1);
        long endTime = System.nanoTime();
        long durationMilliseconds = (endTime - startTime) / 1000000;
        // Output the time taken in seconds, so it can be compared with P2E1_2 & P2E4 if these are run too.
        System.out.println("Time taken in milliseconds by incremental for m = 0, 0.5 & 1: " + durationMilliseconds);

        incrementDrawLine(0, 500, 10, 2);
        incrementDrawLine(0, 500, 10, 5);
        incrementDrawLine(0, 500, 10, 10);
    }

    /**
     * Method that draws lines for angles with slopes of 0 <= m <= 1 using the incremental algorithm as seen in the
     *  portfolio assignment's psuedo-code 2.
     *
     * @param x1 int is the x-coordinate of the line's starting point (so left of x2).
     * @param x2 int is the x-coordinate of the line's ending point (so right of x1).
     * @param c int is the distance from the origin (0,0) to the point where the line would intersect the y-axis (0, y).
     * @param m double the slope at which the line has to be drawn. Must abide to | m | <= 1
     */
    private void incrementDrawLine(int x1, int x2, int c, double m) {
        // Calculate the y for the first point of the line (so the start x1).
        double y = (m * x1) + c;
        for (int i = x1; i < x2; i++) {
            // For every line, draw it on the current column (x-value) at the calculated y.
            StdDraw.point(i, Math.round(y));
            // The next y will be the current y plus the amount of change.
            y = y + m;
        }
    }
}

/**
 * Java class that when run will show my solution for Portfolio 2 (optional) Exercise 5 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P2E5 {
    public static void main(String[] args) {
        new P2E5().run();
    }

    private void run() {
        StdDraw.setScale(0, 512);

        // Keep track of time so we can too compare it to P2E1_2, P2E3 & P2E4 line drawing algorithms.
        long startTime = System.nanoTime();
        bresenDrawLine(10, 10, 40, 30);
        bresenDrawLine(10, 10, 40, 90);
        /* NOTE FOR REPORT:
            Bresenham, as I have implemented it, cannot currently do lines with an m that's higher than 1 (m > 1).
            This results in the second line not being properly drawn, as the algorithm is trying to draw it as steep as it can.
            To fix this, do a check for if m > 1 and flip all calls to x to be y instead and vice versa.
         */
        long endTime = System.nanoTime();
        long durationMilliseconds = (endTime - startTime) / 1000000;
        // Display the time taken to draw the two lines, to compare it to the others, if those are run too.
        System.out.println("Time taken in milliseconds by Bresenham's Midpoint for two short lines: " + durationMilliseconds);
    }

    /**
     * Method that draws lines between 0 and 90 degrees (positive angles) according to Bresenham's algorithm.
     * Consulted sources:
     *  - Flanagan, C. (2019). The Bresenham Line-Drawing Algorithm. [online] Cs.helsinki.fi. Available at: https://www.cs.helsinki.fi/group/goa/mallinnus/lines/bresenh.html [Accessed 27 Feb. 2019].
     *
     * @param x1 int is the x-coordinate of the starting point of the line (assumed to be < x2).
     * @param y1 int is the y-coordinate of the starting point of the line (assumed to be < y2).
     * @param x2 int is the x-coordinate of the ending point of the line (assumed to be > x1).
     * @param y2 int is the y-coordinate of the ending point of the line (assumed to be > y1).
     */
    private void bresenDrawLine(int x1, int y1, int x2, int y2) {
        // Calculate the differences in distance between the two coordinates.
        int dy = y2 - y1;
        int dx = x2 - x1;

        // Draw the initial point from which the line will draw.
        StdDraw.point(x1, y1);

        // Calculate what the slope of this line is.
        double m = (double) dy / dx;
        if (m <= 1) {
            // If m <= 1, it means it's 45 degrees or smaller. In this case, x will change more than y, thus we need to
            //  calculate our differences appropriately.
            double d = 2 * (dy - dx);
            int dE = 2 * dy;
            int dNE = 2 * (dy - dx);

            int y = y1;
            for (int i = (x1 + 1); i < x2; i++) {
                // For each next column, move either right or right & up (next column & row)
                if (d < 0) {
                    d += dE;
                } else {
                    d += dNE;
                    y++;
                }
                // Then draw the point on this new coordinate.
                StdDraw.point(i, y);
            }
        } else if (m > 1) {
            // If m is more than 1, the slope is above 45 degrees, which means the difference in y will be bigger than
            //  that of x, thus we need to flip the differences around.
            double d = 2 * (dx - dy);
            int dE = 2 * dx;
            int dNE = 2 * (dx - dy);

            int x = x1;
            for (int i = (y1 + 1); i < y2; i++) {
                // For each next row, move either up or up & right (next row & column)
                if (d < 0) {
                    d += dE;
                } else {
                    d += dNE;
                    x++;
                }
                // Then draw the point on this new coordinate.
                StdDraw.point(Math.round(x), i);
            }
        }
    }
}

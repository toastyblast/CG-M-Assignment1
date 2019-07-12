/**
 * Java class that when run will show my solution for Portfolio 2 Exercises 1 & 2 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P2E1_2 {
    public static void main(String[] args) {
        new P2E1_2().run();
    }

    private void run() {
        StdDraw.setScale(0, 512);

        // Keep track of the time taken to draw the first 3 lines, as P2E3 & P2E4 will draw the same lines. This way, you can compare the time taken by each.
        long startTime = System.nanoTime();
        bruteDrawLine(0, 500, 10, 0);
        bruteDrawLine(0, 500, 10, .5);
        bruteDrawLine(0, 500, 10, 1);
        long endTime = System.nanoTime();
        long durationMilliseconds = (endTime - startTime) / 1000000;
        // Output the time taken in seconds, so it can be compared with P2E3 & P2E4 if these are run too.
        System.out.println("Time taken in milliseconds by brute-force for m = 0, 0.5 & 1: " + durationMilliseconds);

        bruteDrawLine(0, 500, 10, 2);
        bruteDrawLine(0, 500, 10, 5);
        bruteDrawLine(0, 500, 10, 10);

        /*
        Exercise 2 answers:
        As m increases for each line, you can see that the angle of the line increasingly travels from towards the
         right bottom (at 0, a straight horizontal line) to the top left, where infinity would make a straight vertical
         line. As m increases, the gaps between each point increases too. At m = 0, it's a single line, but at m = 10,
         there are barely any points drawn, with big gaps between each line. This is because of the angle, which causes
         less x coordinates to be travelled. The brute force method does not draw any points in the same column, thus
         creating gaps as it only draws each new x column.
        */
    }

    /**
     * Helper method to draw a line from x1 to x2 at a y start of c and an incline of m.
     *
     * @param x1 int is where the line must start from the left bottom
     * @param x2 int is where the line must end, towards the right top.
     * @param c int place where the line originates compared to the y scale
     * @param m double is the incline of the line from right bottom to left top of the canvas
     */
    private void bruteDrawLine(int x1, int x2, int c, double m) {
        for (int i = x1; i < x2; i++) {
            // For the current column (starting at x1), calculate what the y must be for the line that goes over this
            //  column, then draw the point.
            double y = (m * i) + c;
            StdDraw.point(i, Math.round(y));
        }
    }
}

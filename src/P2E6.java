/**
 * Java class that when run will show my solution for Portfolio 2 (optional) Exercise 6 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P2E6 {
    public static void main(String[] args) {
        new P2E6().run();
    }

    private void run() {
        StdDraw.setScale(0, 512);

        eightWayDrawCircle(256, 256, 100);
        System.out.println();
        eightWayDrawSquare(256, 256, 105);
    }

    /**
     * Method that draws a circle with points, using the eight-way algorithm.
     *
     * Consulted sources:
     *  - www.tutorialspoint.com. (n.d.). Computer Graphics Circle Generation Algorithm. [online] Available at: https://www.tutorialspoint.com/computer_graphics/circle_generation_algorithm.htm [Accessed 29 Jan. 2019].
     *
     * @param x int pixel at which the center of the circle will be located, on the x axis.
     * @param y int pixel at which the center of the circle will be located, on the y axis.
     * @param radius int the amount of pixels the lines of the circle are from the center coordinates.
     */
    private void eightWayDrawCircle(int x, int y, int radius) {
        int angleCount = 0;
        int currentRad = radius;
        int diff = 3 - 2 * radius;

        // While the amount of angles we have drawn the circle from is still less than the total we need for our
        //  radius, draw another 8 points in the next angle. (Since we are just drawing 8 points every time, each
        //  time just at a slightly different angle, from the center coordinates)
        while (angleCount <= currentRad) {
            angleCount++;

            System.out.println(diff);

            // Check if we're drawing on the left side of the center or the right.
            if (diff < 0) {
                // Drawing on the left side...
                diff += 4 * angleCount + 6;
            } else {
                // Drawing on the right side, so we have to adjust our angle to make the full half circle.
                currentRad--;
                diff += 4 * (angleCount - currentRad) + 10;
            }

            // Finally draw the 8 points for our current angle.
            drawShapePoints(x, y, angleCount, currentRad);
        }
    }

    /**
     * Method that draws a square, of which its center will be on the given x & y coordinates.
     * I made this by accident, while trying to figure out how to draw a circle. I found it so amusing and cool that
     *  I figured I'd keep the code in.
     * The difference is that the radius is never changed, unlike with the circle. With the radius not being changed,
     *  it will not stay the radius pixels away from the center, thus creating angles instead of rounded lines.
     * @author Yoran Kerbusch (24143341)
     *
     * @param x int pixel at which the center of the square will be located, on the x axis.
     * @param y int pixel at which the center of the square will be located, on the y axis.
     * @param radius int the amount of pixels the lines of the square are from the center coordinates.
     */
    private void eightWayDrawSquare(int x, int y, int radius) {
        int angleCount = 0;
        int diff = 3 - 2 * radius;

        while (angleCount <= radius) {
            angleCount++;

            System.out.println(diff);

            if (diff < 0) {
                diff += 4 * angleCount + 6;
            } else {
                // To create corners instead of halves of a circle, we do not adjust our radius, and just draw however
                //  far we can with our radius.
                diff += 4 * (angleCount - radius) + 10;
            }

            drawShapePoints(x, y, angleCount, radius);
        }
    }

    /**
     * Helper method that takes a center x and y, as well as the current angle and radius of the circle 8 points to be drawn.
     * @param x int is the x-coordinate of the center of the circle to be drawn as 8 points.
     * @param y int is the y-coordinate of the center of the circle to be drawn as 8 points.
     * @param p int is the amount of change to be done for each point vertically (from x) or horizontally (from y), depending on what half of the circle is being drawn.
     * @param q int is the amount of change to be done for each point horizontally (from y) or vertically (from x), depending on what half of the circle is being drawn.
     */
    private void drawShapePoints(int x, int y, int p, int q) {
        // Draw the top half of the circle's 8 points
        StdDraw.point((x + p), (y + q));
        StdDraw.point((x - p), (y + q));
        StdDraw.point((x + p), (y - q));
        StdDraw.point((x - p), (y - q));
        // Draw the lower half of the circle's 8 points
        StdDraw.point((x + q), (y + p));
        StdDraw.point((x - q), (y + p));
        StdDraw.point((x + q), (y - p));
        StdDraw.point((x - q), (y - p));
    }
}

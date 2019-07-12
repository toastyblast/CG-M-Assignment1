import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Java class that when run will show my solution for Portfolio 4 (optional) Exercise 5 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P4E5 {
    private static Graphics2D offscreen;

    public static void main(String[] args) {
        new P4E5().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.005);

        offscreen = StdDraw.offscreen;

        drawRotatedSquare(250, 250, 240, 0.1, 5);
    }

    /**
     * Method that draws up to 50 squares in a quarter rotating fashion. Stops when 50 squares are drawn, or a 90 degree rotation has been reached.
     * @author Yoran Kerbusch (24143341)
     *
     * @param x double is the x coordinate of the center of the squares, around which they will rotation.
     * @param y double is the y coordinate of the center of the squares, around which they will rotation.
     * @param halfLength double is HALF the initial width and height of the first square, the biggest square.
     * @param scale double is the amount of distance between each square, as each will be smaller than the last.
     * @param rotation double amount each square will rotate, until one reaches 90 degrees of rotation.
     */
    public static void drawRotatedSquare(double x, double y, double halfLength, double scale, double rotation) {
        if (!(halfLength >= 0)) throw new IllegalArgumentException("half length must be nonnegative");
        float scaledPenRadius = (float)(StdDraw.getPenRadius() * StdDraw.DEFAULT_SIZE);
        double xs = StdDraw.scaleX(x);
        double ys = StdDraw.scaleY(y);
        double ws = StdDraw.factorX(2 * halfLength);
        double hs = StdDraw.factorY(2 * halfLength);
        double currentRotation = 90.0;

        if (ws <= 1 && hs <= 1) {
            StdDraw.pixel(x, y);
        } else {
            for (int i = 0; i < 50; i++) {
                // Draw up to 50 squares, and stop once they're all drawn or we are at or beyond 90 degree rotation
                offscreen.rotate(Math.toRadians(currentRotation), xs, ys);
                offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
                offscreen.rotate(Math.toRadians(-currentRotation), xs, ys);

                // scaledPenRadius is deducted from the scale decrease to make (almost) sure that the corners of each square touches their parent square.
                ws -= (ws * scale) - scaledPenRadius;
                hs -= (hs * scale) - scaledPenRadius;
                // We are rotating negatively so that the squares rotate counter-clockwise, like in the example.
                currentRotation -= rotation;

                // We stop once we cannot see squares probably anymore, or when the rotation has reached 90 degrees, like in the example.
                if ((currentRotation < 0) || (ws <= 1 && hs <= 1)) {
                    break;
                }
            }
        }

        StdDraw.draw();
    }
}

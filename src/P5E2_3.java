import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Java class that when run will show my solution for Portfolio 5 Exercise 2 & (optional) 3 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P5E2_3 {
    // Numbers to represent in what mode the small planet is currently turning.
    private static final int STOPPED = 0;
    private static final int CLOCKWISE = 1;
    private static final int COUNTER_CLOCKWISE = 2;
    private static final int RANDOM = 3;

    public static void main(String[] args) {
        new P5E2_3().run();
    }

    private void run() {
        StdDraw3D.setScale(-1, 1);

        // These variables can be altered to change the way the output behaves.
        int numberOfPoints = 360;
        double sunChange = .5;
        int planetMoveMode = CLOCKWISE;
        int turnSpeed = 100;

        // Draw the sun and planet for Portfolio 5 Exercises 2 & 3
        planetSunModel(numberOfPoints, sunChange, planetMoveMode, turnSpeed);
    }

    /**
     * Method that draws a sun and orbiting planet. The sun turns a given amount of degrees per frame, while the planet
     *  orbits around the sun from a given amount of points.
     * P5E3: This also listens for mouse input, changing the turning of the small planet. Left mouse = Clockwise, Right = Counter clockwise & Middle = Stop.
     * @author Yoran Kerbusch (24143341)
     *
     * @param numberOfPoints int the amount of points in the orbit of the smaller planet. The more, the smoother the movement of the planet (recommended between 360 and 720).
     * @param sunChange double the amount of degrees (between 0.0 and 360.0) the wireframe sun turns clockwise each frame.
     * @param planetMoveMode int initial movement mode of the small planet orbiting the wireframe sun.
     * @param turnSpeed int amount of milliseconds StdDraw waits after drawing a frame, before drawing the next frame.
     */
    private void planetSunModel(int numberOfPoints, double sunChange, int planetMoveMode, int turnSpeed) {
        int planetPointer = 0;
        double sunRotationDegree = .0;
        int oldSize = numberOfPoints;
        int planetSteps = numberOfPoints;
        int frameIntervals = turnSpeed;
        boolean updatedPlanet = true;

        double[][] planetPoints = calculatePoints(planetSteps);

        while (true) {
            StdDraw3D.clear();

            // Code for Portfolio 5 Task 3:
            // This checks what mouse button is pressed and switches the rotation mode of the small planet appropriately.
            if (StdDraw3D.mouse1Pressed()) {
                planetMoveMode = CLOCKWISE;
                // Make the sun change positive, if it was negative.
                if (sunChange < 0) sunChange = sunChange * -1;
            } else if (StdDraw3D.mouse2Pressed()) {
                planetMoveMode = STOPPED;
                sunChange = 0;
            } else if (StdDraw3D.mouse3Pressed()) {
                planetMoveMode = COUNTER_CLOCKWISE;
                if (sunChange > 0) sunChange = sunChange * -1;
            }

            if (StdDraw3D.isKeyPressed(KeyEvent.VK_UP)) {
                // The up & down arrow keys change how many milliseconds are between each frame. The up arrow will
                //  decrease the time between frames, making the planet & sun move faster, down to 5 milliseconds.
                if (frameIntervals > 5) {
                    // If the interval minus the change is less than 5 milliseconds, set it to 5 instead.
                    frameIntervals = ((frameIntervals - 5) < 5) ? 5 : (frameIntervals - 5);
                }
            } else if (StdDraw3D.isKeyPressed(KeyEvent.VK_DOWN)) {
                // The down arrow increases the time between frames, slowing the planets down, up to 1000 milliseconds.
                if (frameIntervals < 1000) frameIntervals = ((frameIntervals + 5) > 1000) ? 1000 : (frameIntervals + 5);
            }

            if (StdDraw3D.isKeyPressed(KeyEvent.VK_W)) {
                // The "W" & "S" keys will change how many degrees the planet orbiting the sun moves per frame.
                // "W" decreases the amount of degrees, meaning the planet will move MORE per frame, as low as 90 degrees (4 points) per frame.
                if (planetSteps > 4) {
                    planetSteps--;
                    updatedPlanet = false;
                }
            } else if (StdDraw3D.isKeyPressed(KeyEvent.VK_S)) {
                // "S" increases the amount of degrees, meaning the planet will move LESS per frame, as much as .5 degrees (720 points) per frame.
                if (planetSteps < 720) {
                    planetSteps++;
                    updatedPlanet = false;
                }
            } else if (StdDraw3D.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (!updatedPlanet) {
                    // After changing the amount of angles per frame, press enter to actually change the planet's increments.
                    planetPoints = calculatePoints(planetSteps);
                    // Reset the planetPointer to a new value equivalent to its old location with the new amount of points.
                    planetPointer = (int)(((double)(planetPointer) / (double)(oldSize)) * (double)(planetSteps));
                    oldSize = planetSteps;
                    updatedPlanet = true;
                }
            }

            if (StdDraw3D.isKeyPressed(KeyEvent.VK_A)) {
                // The "A" & "D" keys change how many degrees the sun rotates per frame. "A" making it turn more
                //  clockwise, up to a limit of 180 degrees per frame.
                if (sunChange < 180) {
                    // If sunChange plus the increment is more than 180, just set it to 180.
                    sunChange = ((sunChange + .5) > 180) ? 180 : (sunChange + .5);
                }
            } else if (StdDraw3D.isKeyPressed(KeyEvent.VK_D)) {
                // "D" makes the sun rotate more anti-clockwise, up to a limit of -180 degrees per frame.
                if (sunChange > -180) sunChange = ((sunChange - .5) < -180) ? -180 : (sunChange - .5);
            }

            // Secret random movement mode, if the first letter of my first name is pressed!
            if (StdDraw3D.isKeyPressed(KeyEvent.VK_Y)) planetMoveMode = RANDOM;

            // Create some text on the screen to tell the user what the current settings are.
            StdDraw3D.setPenColor(Color.WHITE);
            StdDraw3D.overlayText(0, .95, "[\u2191/\u2193] Interval (ms): " + frameIntervals);
            StdDraw3D.overlayText(0, .81, "[A/D] Sun rotation: " + sunChange);
            StdDraw3D.overlayText(.65, .95, "Location: " + planetPointer);
            StdDraw3D.overlayText(.65, .88, "Current steps: " + oldSize);
            StdDraw3D.overlayText(.65, .81, "[W/S] New steps: " + planetSteps);
            StdDraw3D.overlayText(.65, .74, "[Enter] Updated?: " + updatedPlanet);

            // Draw the sun at the y angle appropriate for the current degrees.
            StdDraw3D.setPenColor(Color.ORANGE);
            StdDraw3D.wireSphere(0, 0, 0, .45, 0, 0, sunRotationDegree);
            sunRotationDegree = ((sunRotationDegree - sunChange) + 360) % 360;

            // Draw the small planet at the coordinates for the current pointer.
            StdDraw3D.setPenColor(Color.CYAN);
            StdDraw3D.sphere(planetPoints[planetPointer][0], planetPoints[planetPointer][1], 0, .20);

            // Depending on if we have to move clockwise or not, increment the pointer in a loop at all times.
            // This is how the system knows if it should go through the array of points forwards or backwards (or not at all if we are told to be STOPPED)
            StdDraw3D.setPenColor(Color.WHITE);
            if (planetMoveMode == CLOCKWISE) {
                StdDraw3D.overlayText(.3, .64, "[LMB/mmb/rmb] Move mode: Clockwise");
                planetPointer = ((planetPointer - 1) + planetPoints.length) % planetPoints.length;
            } else if (planetMoveMode == COUNTER_CLOCKWISE) {
                StdDraw3D.overlayText(.3, .64, "[lmb/mmb/RMB] Move mode: Anti-clockwise");
                planetPointer = (planetPointer + 1) % planetPoints.length;
            } else if (planetMoveMode == RANDOM) {
                StdDraw3D.overlayText(.3, .64, "[lmb/mmb/rmb/Y] Move mode: Random");
                planetPointer = (int)((Math.random() * planetPoints.length));
            } else {
                StdDraw3D.overlayText(.3, .64, "[lmb/MMB/rmb] Move mode: Stopped");
            }

            StdDraw3D.show(frameIntervals);
        }
    }

    /**
     * Helper method for the small planet. Calculates for each angle the (x, y) coordinates for each point, at a distance of 1.15 from the center.
     * Recommended is a value between 360 and 720 for numberOfPoints, for smooth rotation.
     * @author Yoran Kerbusch (24143341)
     *
     * @param numberOfPoints int is the amount of points we have to calculate coordinates for.
     * @return double[][] array of arrays that holds all the (x,y) coordinates for each point with the given amount of points.
     */
    private double[][] calculatePoints(int numberOfPoints) {
        // Get the angle increment for each point in number of points.
        double degreesPerPoint = (2.0 * Math.PI) / (double)(numberOfPoints);

        double[][] planetPoints = new double[numberOfPoints][];
        for (int i = 1; i <= numberOfPoints; i++) {
            // Then for every point in numberOfPoints, calculate the X and Y values for each of the points.
            double doubleI = (double)(i);
            double pointX = 1.15 * Math.cos(doubleI * degreesPerPoint);
            double pointY = 1.15 * Math.sin(doubleI * degreesPerPoint);

            planetPoints[i - 1] = new double[]{pointX, pointY};
        }

        return planetPoints;
    }
}

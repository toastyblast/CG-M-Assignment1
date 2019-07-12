import java.awt.*;

/**
 * Java class that when run will show my solution for Portfolio 5 Exercise 1 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P5E1 {
    public static void main(String[] args) {
        new P5E1().run();
    }

    private void run() {
        // set the scale of the drawing window
        StdDraw3D.setScale(-1, 1);

        // Set the sky box of the universe to be cyan blue.
        StdDraw3D.clear(Color.CYAN);

        drawHouse();

        // Write some text overlay in the top right corner of the window, with my name as the text.
        StdDraw3D.setPenColor(138,43,226);
        StdDraw3D.overlayText(.65, .95, "Yoran Kerbusch (24143341)");

        // Set the mode and location of the camera to something more fitting.
        StdDraw3D.setCameraMode(StdDraw3D.FPS_MODE);
        StdDraw3D.setCameraPosition(0, .2, 1.75);

        // render to the drawing window
        StdDraw3D.finished();
    }

    /**
     * Method that shows off all sorts of shapes and angles possible to make with StdDraw3D and Java 3D.
     * This was made for Portfolio 5 Exercise 1 to show mastery of the shaping and location working.
     * It draws a house out of all the shapes, both using the standard ones and wireframes, to show the usage of these shapes.
     * @author Yoran Kerbusch (24143341)
     */
    private static void drawHouse() {
        StdDraw3D.setPenColor(Color.LIGHT_GRAY);
        // Draw the floor.
        StdDraw3D.box(0, 0, 0, 1.01, 1.01, .01, 90, 0, 0);
        StdDraw3D.setPenColor(Color.WHITE);
        // Then also draw the ceiling.
        StdDraw3D.box(0, .6, 0, 1.01, 1.01, .01, 90, 0, 0);
        StdDraw3D.setPenColor(Color.GREEN);
        // Then draw a patch of grass in front of the house.
        StdDraw3D.box(0, -0.005, 1.5, 1.01, 0.49, .005, 90, 0, 0);

        StdDraw3D.setPenColor(Color.RED);
        // Draw the side and back walls of the house.
        StdDraw3D.box(0, .3, -1, 1.01, .29, .01, 0, 0, 0);
        StdDraw3D.wireBox(1, .3, 0, 1, .29, .01, 0, 90, 0);
        StdDraw3D.box(-1, .3, 0, 1, .29, .01, 0, 90, 0);
        // The front wall, which consists of multiple parts to allow a door and window to be inserted.
        StdDraw3D.box(.59, .3, 1, .42, .29, .01, 0, 0, 0);
        StdDraw3D.box(0, .5, 1, .17, .09, .01, 0, 0, 0);
        // Draw the left part of the front wall, which has a hole for a window.
        StdDraw3D.box(-.59, .5, 1, .42, .09, .01, 0, 0, 0);
        StdDraw3D.box(-.87, .3, 1, .14, .11, .01, 0, 0, 0);
        StdDraw3D.box(-.31, .3, 1, .14, .11, .01, 0, 0, 0);
        StdDraw3D.box(-.59, .1, 1, .42, .09, .01, 0, 0, 0);

        StdDraw3D.setPenColor(Color.GRAY);
        // Draw the door opening of the house, in the front wall.
        StdDraw3D.box(.15, .2, 1, .02, .21, .02, 0, 0, 0);
        StdDraw3D.box(-.15, .2, 1, .02, .21, .02, 0, 0, 0);
        StdDraw3D.box(0, .39, 1, .17, .02, .02, 0, 0, 0);
        // Draw a little step for the door.
        StdDraw3D.box(0, 0, 1.09, .2, .08, .01, 90, 0, 0);
        // Draw a window in the front wall.
        StdDraw3D.box(-.59, .2, 1, .15, .01, .02, 0, 0, 0);
        StdDraw3D.box(-.73, .3, 1, .09, .01, .02, 0, 0, 90);
        StdDraw3D.box(-.59, .3, 1, .13, .005, .005, 0, 0, 0);
        StdDraw3D.box(-.59, .3, 1, .09, .005, .005, 0, 0, 90);
        StdDraw3D.box(-.45, .3, 1, .09, .01, .02, 0, 0, 90);
        StdDraw3D.box(-.59, .4, 1, .15, .01, .02, 0, 0, 0);

        StdDraw3D.setPenColor(Color.ORANGE);
        // Draw the front and back of the roof.
        StdDraw3D.polygon(new double[]{-1, 0, 1}, new double[]{.61, .81, .61}, new double[]{-1.01, -1.01, -1.01});
        StdDraw3D.polygon(new double[]{-1, 0, 1}, new double[]{.61, .81, .61}, new double[]{1.01, 1.01, 1.01});
        // Draw the roof.
        StdDraw3D.box(-.54, .7, 0, 1.11, .552, .01, 79, 90, 0);
        StdDraw3D.box(.54, .7, 0, 1.11, .552, .01, 79, 270, 0);

        // Draw a few boxes in the house, using cubes (and different angles to place them).
        StdDraw3D.cube(-.61, .17, -.67, .17);
        StdDraw3D.cube(-.71, .11, -.29, .11, 0, 74, 0);
        StdDraw3D.wireCube(-.68, .26, -.2, .04, 0, 38, 0);

        // Draw a few clouds above the house using ellipsoids.
        StdDraw3D.setPenColor(Color.WHITE);
        StdDraw3D.ellipsoid(-.5, 1.4, .1, .7, .25, .3);
        StdDraw3D.wireEllipsoid(-.87, 1.62, .24, .4, .3, .2);

        // Draw some stepping stones in the garden using cylinders.
        StdDraw3D.setPenColor(Color.LIGHT_GRAY);
        StdDraw3D.cylinder(-.07, 0, 1.28, .09, .01);
        StdDraw3D.wireCylinder(.05, 0, 1.41, .05, .01);
        StdDraw3D.cylinder(-.02, 0, 1.49, .03, .01);

        // Draw a few trees in the garden using cones (and cylinders).
        StdDraw3D.setPenColor(Color.ORANGE);
        StdDraw3D.cylinder(.44, 0.05, 1.56, .05, .1);
        StdDraw3D.setPenColor(Color.GREEN);
        StdDraw3D.cone(.44, .35, 1.56, .2, .5);
        StdDraw3D.wireCone(-.32, .15, 1.15, .08, .29);

        // Draw the house number using 3D text next to the door.
        StdDraw3D.setPenColor(Color.BLACK);
        StdDraw3D.text3D(.2, .25, 1.008, "13");
        StdDraw3D.tube(.21, .23, 1.01, .29, .23, 1.01, 0.01);
    }
}

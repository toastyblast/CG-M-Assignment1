/**
 * Java class that when run will show my solution for Portfolio 4 Exercise 3 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P4E3 {
    public static void main(String[] args) {
        new P4E3().run();
    }

    private void run() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 500);
        StdDraw.setPenRadius(0.02);

        animatedTextCircle("Computer Graphics", 250, 250, 150, true, 500);
    }

    /**
     * Method that draws the given sentence in a circle and turns them around at certain milliseconds of intervals.
     * @author Yoran Kerbusch (24143341)
     *
     * @param x int is the center x coordinate of the circle of characters.
     * @param y int is the center y coordinate of the circle of characters.
     * @param radius double is half the width of the circle, calculated from the center point.
     * @param sentence String is the word/sentence to show as characters formed in a circle.
     * @param clockwise boolean dictates which way around the circle of characters turns.
     * @param turnSpeed int is the amount of milliseconds between each shift of the characters.
     */
    private void animatedTextCircle(String sentence, int x, int y, double radius, boolean clockwise, int turnSpeed) {
        if (!sentence.endsWith(" ") && !sentence.startsWith(" ")) {
            // Add a space to the sentence if it does not start or end with one, to make a break between the last and
            //  first word when drawn in the circle.
            sentence = sentence + " ";
        }
        // Split the string into individual characters.
        String[] characters = sentence.split("(?!^)");

        // Keep a pointer, so we know which character to draw at which one of the circle positions.
        int pointer = 0;

        // Generate an array of coordinates for each character.
        double degreesPerPoint = (2 * Math.PI) / characters.length;
        double[][] circleCoords = new double[characters.length][];
        for (int i = 0; i < characters.length; i++) {
            // Calculate the X and Y coordinates for the amount of letters. But instead of cos for X, use sin (and vice versa for Y),
            //  as that will cause the letters to not be in reverse.
            double pointX = Math.round(x - radius * Math.sin((i + 1) * degreesPerPoint));
            double pointY = Math.round(y - radius * Math.cos((i + 1) * degreesPerPoint));
            circleCoords[i] = new double[]{pointX, pointY};
        }

        while (true) {
            // And then each 500 milliseconds, move each letter to the coordinate of the one before it, depending on clockwise or counter-clockwise directions.
            for (int i = 0; i < characters.length; i++) {
                if (clockwise) {
                    // If the character index - the pointer < characters.length, wrap backwards to the end of characters to continue the circle.
                    StdDraw.text(circleCoords[i][0], circleCoords[i][1], characters[((i - pointer) + characters.length) % characters.length]);
                } else {
                    // If the character index + the pointer > characters.length, use module of this length to wrap back to the start.
                    StdDraw.text(circleCoords[i][0], circleCoords[i][1], characters[(i + pointer) % characters.length]);
                }
            }

            StdDraw.show(turnSpeed);
            StdDraw.clear(StdDraw.WHITE);
            // Add one to the pointer. If more than characters.length, wrap back to the start of that array.
            pointer = (pointer + 1) % characters.length;
        }
    }
}

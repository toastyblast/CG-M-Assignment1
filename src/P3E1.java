/**
 * Java class that when run will show my solution for Portfolio 3 Exercise 1 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P3E1 {
    public static void main(String[] args) {
        new P3E1().run();
    }

    private void run() {
        int cWidth = 500;
        int cHeight = 500;
        StdDraw.setCanvasSize(cWidth, cHeight);
        StdDraw.setXscale(0, cWidth);
        StdDraw.setYscale(0, cHeight);

        int boardSize = 25;
        drawCheckers(boardSize);
    }

    /**
     * Method that draws a checkerboard with the given amount of squares (or rectangles if the canvas isn't square in size).
     * @author Yoran Kerbusch (24143341)
     *
     * @param size int is the amount of squares in height and width the checkerboard must have. The bigger, the slower the drawing will be.
     */
    private void drawCheckers(int size) {
        // Some variables to improve readability.
        int x = 0;
        int y = 0;
        // The width and height each square/rectangle must have. This thus adapts to the canvas if the canvas isn't square.
        double squareHeight = StdDraw.height / (double)(size);
        double squareWidth = StdDraw.width / (double)(size);

        for (int i = 0; i < size; i++) {
            // Add 1 to i as otherwise the first column (which would be i = 0), would not be drawn properly, causing
            //  the last row to not be checkered in sync with the other columns.
            double gridX = x + (i + 1) * squareWidth;

            for (int j = 0; j < size; j++) {
                // Add 1 to j as otherwise the first row (which would be j = 0), would not be drawn properly, causing
                //  the last row to not be checkered in sync with the other rows.
                double gridY = y + (j + 1) * squareHeight;

                // At even numbered coordinates (like (0, 0), (1, 3), (4, 2), etc), draw a red square. This makes sure
                //  the origin (left-bottom square, at (0,0)) is always red.
                if ((i + j) % 2 == 0) { StdDraw.setPenColor(StdDraw.RED); }
                else { StdDraw.setPenColor(StdDraw.BLACK); }

                StdDraw.filledRectangle(gridX, gridY, squareWidth, squareHeight);
            }
        }
    }
}

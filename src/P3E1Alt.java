import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Class that does the same as P3E1 but has a slider so you can change how big the checkerboard is without having to change it in-code.
 * This was made as an experiment with Swing and JComponents to create dynamic code that changes during runtime.
 * @author Yoran Kerbusch (24143341)
 *
 * Consulted sources:
 *  - Docs.oracle.com. (n.d.). How to Use Sliders (The Java™ Tutorials > Creating a GUI With JFC/Swing > Using Swing Components). [online] Available at: https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html [Accessed 26 Feb. 2019];
 *  - Docs.oracle.com. (n.d.). Weather Wizard Oracle example code. [online] Available at: https://docs.oracle.com/javase/tutorial/2d/basic2d/examples/WeatherWizard.java [Accessed 26 Feb. 2019].
 */
public class P3E1Alt extends JApplet implements ChangeListener {
    BoardPainter painter;

    public void start() {
        initComponents();
    }

    /**
     * Method that is called upon running P3E1Alt.java, starting the checkerboard tool.
     *
     * @param args String[] are the command line arguments passed to the file, if any (not used by this code).
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("P3E1: Checkerboard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the applet so that we can add a visual frame to it, which can then have visual elements added to it.
        JApplet ap = new P3E1Alt();
        ap.init();
        ap.start();
        f.add("Center", ap);
        // Make the frame visible as soon as the applet has been added.
        f.pack();
        f.setVisible(true);
    }

    /**
     * Method that is called upon startup of the applet. This will create the slider and checkerboard and put them into
     *  the applet's frame, so the user can see them and interact with them.
     */
    public void initComponents() {
        setLayout(new BorderLayout());

        JPanel p = new JPanel();

        JLabel label = new JLabel("Tiles");
        p.add(label);

        int startBoardSize = 10;
        // Set up the JSlider and its position, as well as how many values it will represent and allow to be used.
        JSlider tempSlider = new JSlider(JSlider.VERTICAL, 2, 100, startBoardSize);
        tempSlider.setMinorTickSpacing(1);
        tempSlider.setMajorTickSpacing(49);
        tempSlider.setPaintTicks(true);
        tempSlider.setPaintLabels(true);
        // Add an event listener to the slider of this instance, so we know when the slider is being interacted with.
        tempSlider.addChangeListener(this);
        p.add(tempSlider);
        add("North", p);

        // Create the checkerboard frame to draw next to the JSlider.
        painter = new BoardPainter();
        painter.setBoardSize(startBoardSize);
        p.add("Checkerboard", painter);
    }

    /**
     * Method that is called when the user uses the JSlider element to select the amount of squares.
     *
     * @param e ChangeEvent is the event of the JSlider having changed in value.
     */
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider)e.getSource();
        painter.setBoardSize((slider.getValue() + 1));
    }
}

/**
 * Class that creates the actual checkerboard and allows itself to be changed.
 * @author Yoran Kerbusch (24143341)
 */
class BoardPainter extends Component {
    private int boardGrid;

    void setBoardSize(int temp) {
        boardGrid = temp;
        repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(512, 512);
    }

    /**
     * Same method as the drawCheckers() method in P3E1.java, just adapted to work as a drawing method to work with
     *  Java2D instead of StdDraw.
     * @author Yoran Kerbusch (24143341)
     *
     * @param g Graphics is the Graphics of the frame that the component needs to be drawn in.
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();

        // Some variables to improve readability.
        int x = 0;
        int y = 0;
        // The width and height each square/rectangle must have. This thus adapts to the canvas if the canvas isn't square.
        double squareHeight = size.height / (double)(boardGrid);
        double squareWidth = size.width / (double)(boardGrid);

        for (int i = 0; i < boardGrid; i++) {
            // Add 1 to i as otherwise the first column (which would be i = 0), would not be drawn properly, causing
            //  the last row to not be checkered in sync with the other columns.
            double gridX = x + (i + 1) * squareWidth;

            for (int j = 0; j < boardGrid; j++) {
                // Add 1 to j as otherwise the first row (which would be j = 0), would not be drawn properly, causing
                //  the last row to not be checkered in sync with the other rows.
                double gridY = y + (j + 1) * squareHeight;

                // At even numbered coordinates (like (0, 0), (1, 3), (4, 2), etc), draw a red square. This makes sure
                //  the origin (left-bottom square, at (0,0)) is always red.
                if ((i + j) % 2 == 0) { g2.setColor(Color.RED); }
                else { g2.setColor(Color.BLACK); }

                Rectangle2D.Double rectangle = new Rectangle2D.Double();
                rectangle.setFrame(gridX, gridY, squareWidth, squareHeight);
                Area rect1 = new Area(rectangle);
                g2.fill(rect1);
            }
        }
    }
}
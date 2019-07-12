public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Welcome to Computer Graphics and Modelling Module!");

        // A drawing window always goes from Min = 0 to Max = 1, so values are in Doubles.
        // Things are drawn from left-bottom, so (0,0) is bottom left, (1,1) is top right.

        StdDraw.square(.2, .2, .1); // Center coordinate X = 0.2, Y = 0.2, half-length (radius) = .1

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(.5, .8, "Welcome to Computer Graphics and Modelling Module!");
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(.5, .6, "Our first Graphics 2D Program!");
        StdDraw.text(.5, .5, "Is hij niet prachtig?");
    }
}

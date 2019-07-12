import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Java class that when run will show my solution for Portfolio 4 Exercises 1 & 2 for CIS2160 2018-2019.
 * @author Yoran Kerbusch (24143341)
 */
public class P4E1_2 {
    public static void main(String[] args) {
        new P4E1_2().run();
    }

    private void run() {
        StdDraw.setCanvasSize(512, 512);

        // Draw and animate the balls. You can change the amount of balls. However, adding a lot can cause visual
        //  glitches and balls getting stuck in each other.
        // When "true", tracerMode will not clear the canvas after each frame, causing the balls to leave a trail.
        bounceTheBalls(25, false
        );
    }

    /**
     * Method that continues in an endless loop. It generates a given amount of random balls and bounces these around the canvas.
     * Balls are generated until the given amount or if the system fails to generate a new ball on a free space 5 times.
     * @author Yoran Kerbusch (24143341).
     *
     * @param amountOfBalls int is the amount of balls to generate and subsequently bounce around.
     */
    private void bounceTheBalls(int amountOfBalls, boolean tracerMode) {
        // Set the scale of the coordinate system.
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);

        ArrayList<Ball> balls = new ArrayList<>();
        Random random = new Random();
        int attempts = 5;
        for (int i = 0; i < amountOfBalls; i++) {
            boolean success = true;
            // Create an amount of completely random size, location and speed balls that will bounce around.
            int radius = random.nextInt(76 - 10) + 10;
            int randomRX = random.nextInt((1001 - (radius + 5)) + (1001 - (radius + 5))) - (1001 - (radius + 5));
            int randomRY = random.nextInt((1001 - (radius + 5)) + (1001 - (radius + 5))) - (1001 - (radius + 5));
            int randomVX = random.nextInt(26 + 25) - 25;
            int randomVY = random.nextInt(26 + 25) - 25;
            // Generate random RGB values of up to max 255 (including 255 this must be 256), or in this case up to and including 160 (to create only darker colours).
            int[] newColours = new int[]{random.nextInt(161), random.nextInt(161), random.nextInt(161)};
            Ball newBall = new Ball((randomRX / 1000.0), (randomRY / 1000.0), (randomVX / 1000.0), (randomVY / 1000.0), (radius / 1000.0), newColours);

            if (balls.size() <= 0) {
                // If there are no balls yet, it means all space is free, thus we can place this ball here.
                balls.add(newBall);
                continue;
            }

            for (Ball ball : balls) {
                // Check this new ball with every other ball generated so far to see if it was placed on a free spot.
                if (newBall.checkCollision(ball)) {
                    // Deduct the amount of attempts and check that this isn't a free spot.
                    attempts--;
                    success = false;
                    // Then break out of this for-loop.
                    break;
                }
            }

            if (attempts <= 0) {
                // Break out of the loop as all our attempts are up. Just start with the amount of balls we could generate.
                System.out.println("Ball generation stopped after 5 failed attempts of finding a free space for the next ball.");
                System.out.println("This doesn't mean balls cannot get stuck in each other after their initial spawn location, however. It only ensures no balls are spawned into one another.\n");
                break;
            } else if (!success) {
                // Revert for-loop so it does this same ball number again, hopefully generating correct random location values.
                i--;
            } else {
                // Otherwise, this ball must be generated on a correct spot and thus we can add it to the array, while
                //  resetting the attempts counter for the next ball generation.
                attempts = 3;
                balls.add(newBall);
            }
        }

        System.out.println("Successfully generated " + balls.size() + " of the desired " + amountOfBalls + " random balls.");

        // Set the background to gray.
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        // Loop the animation.
        while (true) {
            if (!tracerMode) {
                // Clear the background, removing the previous frame of the balls, if tracer mode is off.
                StdDraw.clear(StdDraw.LIGHT_GRAY);
            }

            for (int i = 0; i < balls.size(); i++) {
                // Every ball should check for collision with the other balls.
                Ball thisBall = balls.get(i);

                for (int j = (i + 1); j < balls.size(); j++) {
                    // Each thisBall should check every otherBall after it. Not any of the balls before it in the array
                    //  though, as those already checked themselves with thisBall.
                    Ball otherBall = balls.get(j);

                    if (thisBall.checkCollision(otherBall)) {
                        // Calculate the velocities (and thus directions) of the balls after colliding, using code from Jamieson D.'s solution.
                        double[] velocitiesThisBall = calculateElasticity(thisBall, otherBall);
                        double[] velocitiesOtherBall = calculateElasticity(otherBall, thisBall);
                        // Then set the velocities of both balls to these new velocities.
                        thisBall.setVx(velocitiesThisBall[0]);
                        thisBall.setVy(velocitiesThisBall[1]);
                        otherBall.setVx(velocitiesOtherBall[0]);
                        otherBall.setVy(velocitiesOtherBall[1]);
                    }
                }

                // Let the ball calculate the coordinates of where it will move next.
                double[] movement = thisBall.update();
                int[] ballColour = thisBall.getColour();

                StdDraw.setPenColor(ballColour[0], ballColour[1], ballColour[2]);
                // Use the coordinates the ball returned to draw the ball in the canvas.
                StdDraw.filledCircle(movement[0], movement[1], movement[2]);
            }

            // Display the ball, then basically .wait() for N amount of milliseconds.
            StdDraw.show(20);
        }
    }

    /**
     * Helper method that calculates the new x and y velocities for a ball after colliding with another ball.
     * @author Darren Jamieson @ Envator Tuts+
     * Calculation for elasticity was taken from source:
     *  - Jamieson, D. (2012). When Worlds Collide: Simulating Circle-Circle Collisions. [online] Game Development Envato Tuts+. Available at: https://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-simulating-circle-circle-collisions--gamedev-769 [Accessed 28 Feb. 2019].
     *
     * @param thisBall Ball is the ball we want to calculate the new velocities and directions for.
     * @param otherBall Ball is the ball thisBall is colliding with, and will thus influence the new direction and speed of thisBall.
     * @return double[] array with the new x and y velocities for thisBall.
     */
    private double[] calculateElasticity(Ball thisBall, Ball otherBall) {
        double firstMass = thisBall.getRadius();
        double secondMass = otherBall.getRadius();
        // Calculate the new velocities for thisBall using ELASTIC COLLISION. To keep the program simple, assume the mass of each ball is equal to their radius.
        double newXVelocity = (thisBall.getVx() * (firstMass - secondMass) + (2 * secondMass * otherBall.getVx())) / (firstMass + secondMass);
        double newYVelocity = (thisBall.getVy() * (firstMass - secondMass) + (2 * secondMass * otherBall.getVy())) / (firstMass + secondMass);

        return new double[]{newXVelocity, newYVelocity};
    }

    /**
     * A ball that is shown in the window that is outputted by this code. The ball will move at a set speed
     * @author Yoran Kerbusch (24143341)
     */
    private class Ball {
        private double rx, ry, vx, vy, radius;
        private int[] colour;

        public Ball(double rx, double ry, double vx, double vy, double radius, int[] colour) {
            this.rx = rx;
            this.vx = vx;
            this.ry = ry;
            this.vy = vy;
            this.radius = radius;
            this.colour = colour;
        }

        /**
         * Method that can be called so the ball calculates what its next (x,y) coordinates/position has to be, according
         *  to its current coordinates, directions and speed.
         * @author Yoran Kerbusch (24143341)
         *
         * @return double[] array with the (x,y) coordinates of the next position the ball is at, within the canvas.
         */
        public double[] update() {
            // Bounce off the wall according to the law of elastic collision.
            if (Math.abs(this.rx + this.vx) > (1.0 - this.radius)) { this.vx = -this.vx; }
            if (Math.abs(this.ry + this.vy) > (1.0 - this.radius)) { this.vy = -this.vy; }

            // Update the ball's position.
            this.rx += this.vx;
            this.ry += this.vy;

            // Return an array with the new data so that the system calling can draw the ball.
            return new double[]{this.rx, this.ry, this.radius};
        }

        /**
         * Method that checks if this ball and the other given ball are colliding or not.
         * @author Yoran Kerbusch (24143341)
         *
         * @param otherBall Ball is another ball we want to check this current one with. Must not be this same ball.
         * @return boolean telling the calling system if this ball is colliding with the given otherBall.
         */
        public boolean checkCollision(Ball otherBall) {
            // Calculate the distance between this ball and the other ball using trigonometry.
            double distance = Math.hypot((this.rx - otherBall.getCurrentX()), (this.ry - otherBall.getCurrentY()));

            if (distance < (this.radius + otherBall.getRadius())) {
                // If the calculated distance is less than the radius of this ball plus that of the other ball, then
                //  the two balls are colliding.
                return true;
            }
            return false;
        }

        public double getCurrentX() {
            return this.rx;
        }

        public double getCurrentY() {
            return this.ry;
        }

        public double getVx() {
            return vx;
        }

        public double getVy() {
            return vy;
        }

        public void setVx(double vx) {
            this.vx = vx;
        }

        public void setVy(double vy) {
            this.vy = vy;
        }

        public double getRadius() {
            return radius;
        }

        public int[] getColour() {
            return colour;
        }

        @Override
        public String toString() {
            return "rx: " + this.rx + " ~ vx: " + this.vx + " ~ ry: " + this.ry + " ~ vy: " + this.vy + " ~ radius: " + this.radius;
        }
    }
}

package game;

import basic.Collidable;
import sprite.Sprite;
import basic.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;

import java.util.List;
import java.util.ArrayList;

/**
 * classname: Paddle
 * Creates the player in the game.
 *
 * @author Narkis Kremizi
 * @version 1.0 19/04/2018
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle paddle;
    private java.awt.Color fillColor;
    private biuoop.KeyboardSensor keyboard;
    private double speedOfPaddle;
    private final int height = 600;
    private final int width = 800;

    /**
     * A constructor that creates a paddle consisting of a rectangle, a game, a GUI, and a keyboard.
     *
     * @param paddle        paddle
     * @param g             current game
     * @param speedOfPaddle the movement of the paddle
     * @param fillColor     fill color
     */
    public Paddle(Rectangle paddle, GameLevel g, double speedOfPaddle, java.awt.Color fillColor) {
        this.paddle = paddle;
        GameLevel game = g;
        this.speedOfPaddle = speedOfPaddle;
        this.keyboard = g.getKeyboard();
        this.fillColor = fillColor;
    }

    /**
     * move the paddle to the left.
     *
     * @param dt frame rate
     */
    public void moveLeft(double dt) {
        int dx = (int) ((double) this.speedOfPaddle * dt);
        this.paddle.setUpperLeft(new Point(this.paddle.getUpperLeft().getX() - dx, this.paddle.getUpperLeft().getY()));
    }

    /**
     * move the paddle to the right.
     *
     * @param dt frame rate
     */
    public void moveRight(double dt) {
        int dx = (int) ((double) this.speedOfPaddle * dt);
        this.paddle.setUpperLeft(new Point(this.paddle.getUpperLeft().getX() + dx, this.paddle.getUpperLeft().getY()));
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt frame rate
     */
    public void timePassed(double dt) {
        /* If the user moves left on the keyboard and it will not get him out of the screen - move the paddle to the
         left. */
        if ((keyboard.isPressed(KeyboardSensor.LEFT_KEY)) && (this.paddle.getUpperLeft().getX() > 20)) {
            this.moveLeft(dt);
        }
        /* If the user moves right on the keyboard - move the paddle to the right and it will not get him out of the
         screen. */
        if ((keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) && (this.paddle.getUpperLeft().getX()
                + this.paddle.getWidth() < this.width - 20)) {
            this.moveRight(dt);
        }
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        /* Set the color of the paddle. */
        d.setColor(this.fillColor);
        /* fill the paddle. */
        d.fillRectangle((int) (this.paddle.getUpperLeft().getX()), (int) (this.paddle.getUpperLeft().getY()),
                (int) (this.paddle.getWidth()), (int) (this.paddle.getHeight()));
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  collision point
     * @param currentVelocity the velocity before the collision
     * @param hitter          a ball
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point upperLeft = this.paddle.getUpperLeft();
        /* Calculate the width of each part in the paddle after dividing it into five equal regions. */
        double regionWidth = this.paddle.getWidth() / 5;
        /* Calculate the speed of the velocity using Pythagorean theorem. */
        double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        List<Point> regionStartPoints = new ArrayList<>();
        /* Create starting points for all region. */
        for (int i = 0; i < 6; i++) {
            regionStartPoints.add(new Point(upperLeft.getX() + regionWidth * i, upperLeft.getY()));
        }
        /* If the point is in region 1 we bounce back the ball with an angle of 300 degrees. */
        if ((collisionPoint.getX() >= regionStartPoints.get(0).getX()) && (collisionPoint.getX()
                < regionStartPoints.get(1).getX())) {
            return Velocity.fromAngleAndSpeed(300, speed);
        }
        /* If the point is in region 1 we bounce back the ball with an angle of 330 degrees. */
        if ((collisionPoint.getX() >= regionStartPoints.get(1).getX()) && (collisionPoint.getX()
                < regionStartPoints.get(2).getX())) {
            return Velocity.fromAngleAndSpeed(330, speed);
        }
        /* If the point is in region 3 we will change its dy to -dy. */
        if ((collisionPoint.getX() >= regionStartPoints.get(2).getX()) && (collisionPoint.getX()
                < regionStartPoints.get(3).getX())) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        /* If the point is in region 1 we bounce back the ball with an angle of 30 degrees. */
        if ((collisionPoint.getX() >= regionStartPoints.get(3).getX()) && (collisionPoint.getX()
                < regionStartPoints.get(4).getX())) {
            return Velocity.fromAngleAndSpeed(30, speed);
        }
        /* If the point is in region 1 we bounce back the ball with an angle of 60 degrees. */
        if ((collisionPoint.getX() >= regionStartPoints.get(4).getX()) && (collisionPoint.getX()
                < regionStartPoints.get(5).getX())) {
            return Velocity.fromAngleAndSpeed(60, speed);
        }
        return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
    }

    /**
     * Add this paddle to the game.
     *
     * @param g the current game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * put the paddle in the middle.
     */
    public void putPaddleInTheMiddle() {
        this.paddle.setUpperLeft(new Point((this.width / 2) - (this.paddle.getWidth() / 2), this.height - 30));
    }
}
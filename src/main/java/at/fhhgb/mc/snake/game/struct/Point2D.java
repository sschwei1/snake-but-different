package at.fhhgb.mc.snake.game.struct;

import at.fhhgb.mc.snake.game.Snake;

public class Point2D implements Cloneable {
    private int x;
    private int y;

    private int maxX;
    private int maxY;

    public Point2D() {
        this(0, 0);
    }

    public Point2D(int x, int y) {
        this(x, y, -1, -1);
    }

    public Point2D(int x, int y, int maxX, int maxY) {
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getX() {
        return x;
    }

    public Point2D setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Point2D setY(int y) {
        this.y = y;
        return this;
    }

    public int getMaxX() {
        return maxX;
    }

    public Point2D setMaxX(int maxX) {
        this.maxX = maxX;
        return this;
    }

    public int getMaxY() {
        return maxY;
    }

    public Point2D setMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public void move(Snake.Direction direction) {
        switch (direction) {
            case UP:    this.y -= 1; break;
            case DOWN:  this.y += 1; break;
            case LEFT:  this.x -= 1; break;
            case RIGHT: this.x += 1; break;
        }

        if(this.maxY > 0) {
            this.y += this.maxY;
            this.y %= this.maxY;
        }

        if(this.maxX > 0) {
            this.x += this.maxX;
            this.x %= this.maxX;
        }
    }

    public int asInt() {
        if(this.maxX <= 0 || this.maxY <= 0) {
            throw new IllegalStateException("Cannot convert to int without maxX and maxY set.");
        }

        return this.x + this.y * this.maxX;
    }

    @Override
    public Point2D clone() {
        try {
            return (Point2D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String toString() {
        return String.format("Point2D(%d, %d)", this.x, this.y);
    }

    public boolean equals(Point2D point) {
        if(point == null) {
            return false;
        }

        return this.x == point.x && this.y == point.y;
    }
}

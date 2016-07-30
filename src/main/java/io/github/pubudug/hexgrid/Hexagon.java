package io.github.pubudug.hexgrid;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class Hexagon<C extends Coordinate> {
    private int size;
    private C coordinate;

    protected Hexagon(C coordinate, int size) {
        this.coordinate = coordinate;
        this.size = size;
    }

    public Point getCenter() {
        double x = (double) size * 3 / 2 * coordinate.getCubeX();
        double y = size * Math.sqrt(3) * (coordinate.getCubeZ() + (double) coordinate.getCubeX() / 2);
        return new Point(x, y);
    }

    Point getCorner(Corner corner) {
        double angle = Math.toRadians(60 * corner.getId());
        Point c = getCenter();
        return new Point(c.getX() + size * cos(angle), c.getY() + size * sin(angle));
    }

    protected List<Point> getCorners() {
        List<Point> corners = new LinkedList<>();
        for (Corner corner : Corner.values()) {
            corners.add(getCorner(corner));
        }
        return corners;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}

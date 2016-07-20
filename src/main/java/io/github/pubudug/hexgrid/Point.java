package io.github.pubudug.hexgrid;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Point {
    private double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

}

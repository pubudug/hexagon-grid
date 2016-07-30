package io.github.pubudug.hexgrid;

public interface CoordinateFactory<C extends Coordinate> {
    C create(Coordinate c);

    C[][] createArray(int columns, int rows);
}

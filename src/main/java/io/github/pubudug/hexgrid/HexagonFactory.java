package io.github.pubudug.hexgrid;

public interface HexagonFactory<T extends Hexagon> {

    T[][] createArray(int columns, int rows);

    T createHexagon(Coordinate c, int size);

}

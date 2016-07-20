package io.github.pubudug.hexgrid;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Math.round;
import static java.lang.Math.abs;

public class Grid<T extends Hexagon> {

    private T[][] hexagons;
    private Map<T, T> hexagonMap;
    private int size;

    Grid(HexagonFactory<T> hexagonFactory, int columns, int rows, int size) {
        this.hexagonMap = new HashMap<>();
        this.hexagons = hexagonFactory.createArray(columns, rows);
        this.size = size;
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Coordinate c = Coordinate.fromOffsetCoordinates(column, row);
                T hexagon = hexagonFactory.createHexagon(c, size);
                hexagons[column][row] = hexagon;
                hexagonMap.put(hexagon, hexagon);
            }
        }
    }

    Hexagon getHexagonContainingPixel(int x, int y) {
        double cubeX = (double) x * 2 / 3 / size;
        double cubeZ = (-x / 3 + Math.sqrt(3) / 3 * y) / size;
        Coordinate coordinate = roundToCubeCoordinate(cubeX, -cubeX - cubeZ, cubeZ);
        return hexagonMap.get(coordinate);
    }

    private Coordinate roundToCubeCoordinate(double x, double y, double z) {
        long rx = round(x);
        long ry = round(y);
        long rz = round(z);

        double xDiff = abs(rx - x);
        double yDiff = abs(ry - y);
        double zDiff = abs(rz - z);

        if (xDiff > yDiff && xDiff > zDiff) {
            rx = -ry - rz;
        } else if (yDiff > zDiff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }

        return Coordinate.fromCubeCoordinates((int) rx, (int) ry, (int) rz);
    }

    Collection<T> getHexagons() {
        return this.hexagonMap.values();
    }
}
package io.github.pubudug.hexgrid;

import lombok.Getter;

@Getter
public enum Direction {
    NORTH(0, 1, -1), SOUTH(0, -1, 1), NORTHEAST(1, 0, -1), SOUTHWEST(-1, 0, 1), NORTHWEST(-1, 1, 0), SOTHEAST(1, -1, 0);

    private Coordinate coordinate;

    Direction(int cubeX, int cubeY, int cubeZ) {
        coordinate = Coordinate.fromCubeCoordinates(cubeX, cubeY, cubeZ);
    }

}

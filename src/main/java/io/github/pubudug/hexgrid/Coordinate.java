package io.github.pubudug.hexgrid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.abs;

@Getter(value = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Coordinate {
    private int cubeX;
    private int cubeZ;

    Coordinate(Coordinate coordinate) {
        this.cubeX = coordinate.cubeX;
        this.cubeZ = coordinate.cubeZ;
    }

    private Coordinate(int cubeX, int cubeY, int cubeZ) {
        if (cubeX + cubeY + cubeZ != 0) {
            throw new IllegalArgumentException("Sum of cubeX,cubeY,cubeZ must be zero.");
        }
        this.cubeX = cubeX;
        this.cubeZ = cubeZ;
    }

    private Coordinate(int offsetColumn, int offsetRow) {
        this.cubeX = offsetColumn;
        this.cubeZ = offsetRow - (offsetColumn - (offsetColumn & 1)) / 2;
    }

    static Coordinate fromCubeCoordinates(int cubeX, int cubeY, int cubeZ) {
        return new Coordinate(cubeX, cubeY, cubeZ);
    }

    static Coordinate fromOffsetCoordinates(int offsetColumn, int offsetRow) {
        return new Coordinate(offsetColumn, offsetRow);
    }

    protected int getCubeY() {
        return -cubeX - cubeZ;
    }

    public int getOffsetCoordinateColumn() {
        return cubeX;
    }

    public int getOffsetCoordinateRow() {
        return cubeZ + (cubeX - (cubeX & 1)) / 2;
    }

    int disntaceTo(Coordinate other) {
        return subtract(other).length();
    }

    private Coordinate subtract(Coordinate other) {
        return new Coordinate(cubeX - other.getCubeX(), getCubeY() - other.getCubeY(), cubeZ - other.getCubeZ());
    }

    private Coordinate add(Coordinate other) {
        return new Coordinate(cubeX + other.getCubeX(), getCubeY() + other.getCubeY(), cubeZ + other.getCubeZ());
    }

    private int length() {
        return (abs(cubeX) + abs(getCubeY()) + (cubeZ)) / 2;
    }

    Coordinate getNeighbour(Direction direction) {
        return this.add(direction.getCoordinate());
    }
}

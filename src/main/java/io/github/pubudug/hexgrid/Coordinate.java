package io.github.pubudug.hexgrid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    static Coordinate roundToCubeCoordinate(double x, double y, double z) {
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

        return fromCubeCoordinates((int) rx, (int) ry, (int) rz);
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

    int distanceTo(Coordinate other) {
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

    Set<Coordinate> getNeighbours() {
        Set<Coordinate> neighbours = new HashSet<>();
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            neighbours.add(getNeighbour(direction));
        }
        return neighbours;
    }

    Stream<Coordinate> drawLine(Coordinate to) {
        int distance = distanceTo(to);
        if (distance == 0) {
            return Stream.empty();
        }
        return IntStream.range(0, distance + 1).mapToObj(i -> Integer.valueOf(i)).map(i -> {
            return cubeLinearInterpolate(to, 1.0 * i / distance);
        });
    }

    private Coordinate cubeLinearInterpolate(Coordinate to, double sample) {
        return roundToCubeCoordinate(linearInterpolate(getCubeX(), to.getCubeX(), sample),
                linearInterpolate(getCubeY(), to.getCubeY(), sample),
                linearInterpolate(getCubeZ(), to.getCubeZ(), sample));
    }

    private double linearInterpolate(int from, int to, double sample) {
        return from + (to - from) * sample;
    }
}

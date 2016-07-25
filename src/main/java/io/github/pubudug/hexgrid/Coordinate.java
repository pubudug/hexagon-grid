package io.github.pubudug.hexgrid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter(value = AccessLevel.PROTECTED)
@EqualsAndHashCode
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
        return (abs(cubeX) + abs(getCubeY()) + abs(cubeZ)) / 2;
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

    Stream<Stream<Coordinate>> drawLine(Coordinate toExcluded) {
        int distance = distanceTo(toExcluded);
        if (distance == 0) {
            return Stream.empty();
        }
        double f = 0.01;
        //rather than drawing a line to a center, we draw lines to 3 points around the center,
        //because there are some situations where 2 hexagons match, but rounding chooses only
        //a single hexagon
        double[][] tieBreaker = new double[][] { { f, 0, -f }, { 0, -f, f }, { -f, f, 0 } };
        return Arrays.stream(tieBreaker).map(arr -> {
            return IntStream.range(1, distance).mapToObj(i -> Integer.valueOf(i)).map(i -> {
                return cubeLinearInterpolate(toExcluded.getCubeX() + arr[0], toExcluded.getCubeY() + arr[1],
                        toExcluded.getCubeZ() + arr[2], 1.0 * i / distance);
            });
        });
    }

    private Coordinate cubeLinearInterpolate(double toX, double toY, double toZ, double sample) {
        return roundToCubeCoordinate(linearInterpolate(getCubeX(), toX, sample),
                linearInterpolate(getCubeY(), toY, sample), linearInterpolate(getCubeZ(), toZ, sample));
    }

    private double linearInterpolate(int from, double to, double sample) {
        return from + (to - from) * sample;
    }

    @Override
    public String toString() {
        return "Column : " + getOffsetCoordinateColumn() + " Row: " + getOffsetCoordinateRow();
    }

    public Set<Coordinate> getWithinRange(int range) {
        Set<Coordinate> withinRange = new HashSet<>();
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = max(-range, -dx - range); dy <= min(range, -dx + range); dy++) {
                int dz = -dx - dy;
                withinRange.add(this.add(fromCubeCoordinates(dx, dy, dz)));
            }
        }
        return withinRange;
    }
}

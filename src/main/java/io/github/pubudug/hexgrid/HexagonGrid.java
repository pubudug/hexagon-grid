package io.github.pubudug.hexgrid;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HexagonGrid<T extends Hexagon> {

    private T[][] hexagons;
    private Map<T, T> hexagonMap;
    private int size;
    private int columns;
    private int rows;

    protected HexagonGrid(HexagonFactory<T> hexagonFactory, int columns, int rows, int size) {
        this.hexagonMap = new HashMap<>();
        this.hexagons = hexagonFactory.createArray(columns, rows);
        this.columns = columns;
        this.rows = rows;
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

    public T getHexagonContainingPixel(int x, int y) {
        double cubeX = (double) x * 2 / 3 / size;
        double cubeZ = (-x / 3 + Math.sqrt(3) / 3 * y) / size;
        Coordinate coordinate = Coordinate.roundToCubeCoordinate(cubeX, -cubeX - cubeZ, cubeZ);
        return hexagonMap.get(coordinate);
    }

    protected Collection<T> getHexagons() {
        return this.hexagonMap.values();
    }

    public T getHexagon(int column, int row) {
        return hexagons[column][row];
    }

    public Set<T> getNeighborsOf(T hexagon) {
        Set<Coordinate> neighbourCoordinates = hexagon.getNeighbours();
        Set<T> neighbours = new HashSet<>();
        for (Coordinate coordinate : neighbourCoordinates) {
            if (hexagonMap.containsKey(coordinate)) {
                neighbours.add(hexagonMap.get(coordinate));
            }
        }
        return neighbours;
    }

    public Optional<T> getHexagon(Coordinate c) {
        if (c.getOffsetCoordinateColumn() >= 0 && c.getOffsetCoordinateColumn() < columns
                && c.getOffsetCoordinateRow() >= 0 && c.getOffsetCoordinateRow() < rows) {
            return Optional.of(this.getHexagon(c.getOffsetCoordinateColumn(), c.getOffsetCoordinateRow()));
        } else {
            return Optional.empty();
        }
    }

    public Set<T> getHexagonsWithinRange(Hexagon hexagon, int range) {
        Set<Coordinate> withinRange = hexagon.getWithinRange(range);
        Set<T> hexagonsWithinRange = new HashSet<>();
        for (Coordinate coordinate : withinRange) {
            if (hexagonMap.containsKey(coordinate)) {
                hexagonsWithinRange.add(hexagonMap.get(coordinate));
            }
        }
        return hexagonsWithinRange;
    }

}

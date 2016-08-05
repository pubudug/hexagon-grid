package io.github.pubudug.hexgrid;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoordinateGrid<C extends Coordinate> {

    private int rows;
    private int columns;
    private CoordinateFactory<C> coordinateFactory;
    private C[][] coordinates;

    public CoordinateGrid(int columns, int rows, CoordinateFactory<C> coordinateFactory) {
        this.rows = rows;
        this.columns = columns;
        this.coordinateFactory = coordinateFactory;
        this.coordinates = coordinateFactory.createArray(columns, rows);
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Coordinate c = Coordinate.fromOffsetCoordinates(column, row);
                C coordinate = coordinateFactory.create(c);
                coordinates[column][row] = coordinate;
            }
        }
    }

    public Set<C> getNeighborsOf(C coordinate) {
        Set<Coordinate> result = coordinate.getNeighbours();
        result.removeIf(c -> isOutsideGrid(c));
        return result.stream().map(c -> coordinateFactory.create(c)).collect(Collectors.toSet());
    }

    private boolean isOutsideGrid(Coordinate c) {
        return c.getOffsetCoordinateRow() < 0 || c.getOffsetCoordinateRow() >= rows || c.getOffsetCoordinateColumn() < 0
                || c.getOffsetCoordinateColumn() >= columns;
    }

    public Set<C> getCoordinatesWithinRange(C coordinate, int range) {
        Set<Coordinate> result = coordinate.getWithinRange(range);
        result.removeIf(c -> isOutsideGrid(c));
        return result.stream().map(c -> coordinateFactory.create(c)).collect(Collectors.toSet());
    }

    public Stream<C> getCoordinates() {
        return Arrays.stream(coordinates).flatMap(row -> Arrays.stream(row).map(c -> c));
    }

    public C getCoordinate(int offsetCoordinateColumn, int offsetCoordinateRow) {
        return coordinates[offsetCoordinateColumn][offsetCoordinateRow];
    }

    public Optional<C> getCoordinate(Coordinate c) {
        return isOutsideGrid(c) ? Optional.empty()
                : Optional.of(coordinates[c.getOffsetCoordinateColumn()][c.getOffsetCoordinateRow()]);
    }

}

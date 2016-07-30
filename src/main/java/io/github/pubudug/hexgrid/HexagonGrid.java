package io.github.pubudug.hexgrid;

import java.util.stream.Stream;

public class HexagonGrid<H extends Hexagon<C>, C extends Coordinate> {

    private int size;
    private HexagonFactory<H, C> hexagonFactory;
    private CoordinateGrid<C> coordinateGrid;

    protected HexagonGrid(CoordinateGrid<C> coordinateGrid, HexagonFactory<H, C> hexagonFactory, int size) {
        this.size = size;
        this.hexagonFactory = hexagonFactory;
        this.coordinateGrid = coordinateGrid;

    }

    public H getHexagonContainingPixel(int x, int y) {
        double cubeX = (double) x * 2 / 3 / size;
        double cubeZ = (-x / 3 + Math.sqrt(3) / 3 * y) / size;
        Coordinate c = Coordinate.roundToCubeCoordinate(cubeX, -cubeX - cubeZ, cubeZ);
        return hexagonFactory
                .create(coordinateGrid.getCoordinate(c.getOffsetCoordinateColumn(), c.getOffsetCoordinateRow()));
    }

    protected Stream<H> getHexagons() {
        return this.coordinateGrid.getCoordinates().map(c -> hexagonFactory.create(c));
    }

}

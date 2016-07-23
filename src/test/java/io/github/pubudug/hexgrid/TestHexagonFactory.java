package io.github.pubudug.hexgrid;

import io.github.pubudug.hexgrid.Coordinate;
import io.github.pubudug.hexgrid.HexagonFactory;

public class TestHexagonFactory implements HexagonFactory<TestHexagon> {

    private TerrainMap map;

    public TestHexagonFactory(TerrainMap map) {
        this.map = map;
    }

    @Override
    public TestHexagon[][] createArray(int columns, int rows) {
        return new TestHexagon[columns][rows];
    }

    @Override
    public TestHexagon createHexagon(Coordinate c, int size) {
        return new TestHexagon(c, size, map.getTerrain(c.getOffsetCoordinateColumn(), c.getOffsetCoordinateRow()));
    }

}

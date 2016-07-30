package io.github.pubudug.hexgrid;

public class TestCoordinateFactory implements CoordinateFactory<TestCoordinate> {

    private TerrainMap terrainMap;

    public TestCoordinateFactory(TerrainMap terrainMap) {
        this.terrainMap = terrainMap;
    }

    @Override
    public TestCoordinate create(Coordinate c) {
        return new TestCoordinate(c, terrainMap.getTerrain(c.getOffsetCoordinateColumn(), c.getOffsetCoordinateRow()));
    }

    @Override
    public TestCoordinate[][] createArray(int columns, int rows) {
        return new TestCoordinate[columns][rows];
    }
}

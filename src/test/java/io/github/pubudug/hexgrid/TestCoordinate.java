package io.github.pubudug.hexgrid;

public class TestCoordinate extends Coordinate {
    private TerrainType type;

    public TestCoordinate(Coordinate coordinate, TerrainType terrainType) {
        super(coordinate);
        this.type = terrainType;
    }

    public TerrainType getTerrainType() {
        return type;
    }
}

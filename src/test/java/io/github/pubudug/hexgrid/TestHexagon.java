package io.github.pubudug.hexgrid;

public class TestHexagon extends Hexagon {

    private TerrainType type;

    protected TestHexagon(Coordinate coordinate, int size, TerrainType terrainType) {
        super(coordinate, size);
        this.type = terrainType;
    }

    public TerrainType getTerrainType() {
        return type;
    }

}

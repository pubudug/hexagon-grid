package io.github.pubudug.hexgrid;

import static io.github.pubudug.hexgrid.TerrainType.*;

public class HumanHexagonAttributes implements HexagonAttributes<TestHexagon> {

    @Override
    public int getMovementCost(TestHexagon hexagon) {
        if (TREES.equals(hexagon.getTerrainType())) {
            return 2;
        } else if (WATER.equals(hexagon.getTerrainType())) {
            return 4;
        } else if (HILLS.equals(hexagon.getTerrainType())) {
            return 3;
        } else if (FLAT.equals(hexagon.getTerrainType())) {
            return 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

}

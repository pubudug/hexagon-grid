package io.github.pubudug.hexgrid;

import static io.github.pubudug.hexgrid.TerrainType.*;

public class HumanHexagonAttributes<H extends TestHexagon> implements HexagonAttributes<H> {

    @Override
    public int getMovementCost(H hexagon) {
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

    @Override
    public boolean blocksView(H hexagon) {
        return HILLS.equals(hexagon.getTerrainType()) || TREES.equals(hexagon.getTerrainType());
    }

}

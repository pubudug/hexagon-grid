package io.github.pubudug.hexgrid;

import static io.github.pubudug.hexgrid.TerrainType.*;

public class HumanCoordinateAttributes<C extends TestCoordinate> implements CoordinateAttributes<C> {

    @Override
    public int getMovementCost(C coordinate) {
        if (TREES.equals(coordinate.getTerrainType())) {
            return 2;
        } else if (WATER.equals(coordinate.getTerrainType())) {
            return 4;
        } else if (HILLS.equals(coordinate.getTerrainType())) {
            return 3;
        } else if (FLAT.equals(coordinate.getTerrainType())) {
            return 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean blocksView(C coordinate) {
        return HILLS.equals(coordinate.getTerrainType()) || TREES.equals(coordinate.getTerrainType());
    }

}

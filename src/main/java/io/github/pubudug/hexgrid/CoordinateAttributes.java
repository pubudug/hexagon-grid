package io.github.pubudug.hexgrid;

public interface CoordinateAttributes<C extends Coordinate> {
    int getMovementCost(C c);

    boolean blocksView(C coordinate);

}

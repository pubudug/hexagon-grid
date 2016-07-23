package io.github.pubudug.hexgrid;

public interface HexagonAttributes<T extends Hexagon> {

    int getMovementCost(T c);
}

package io.github.pubudug.hexgrid;

public interface HexagonFactory<H extends Hexagon, C extends Coordinate> {

    H create(C c);

}

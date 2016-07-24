package io.github.pubudug.hexgrid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class VisibilityCalculator<T extends Hexagon> {
    private HexagonGrid<T> grid;
    private int range;
    private Hexagon hexagon;
    private HexagonAttributes<T> hexagonAttributes;

    public VisibilityCalculator(Hexagon from, int range, HexagonGrid<T> grid, HexagonAttributes<T> attributes) {
        this.range = range;
        this.hexagon = from;
        this.grid = grid;
        this.hexagonAttributes = attributes;
    }

    public Set<T> getVisibleHexagons() {
        Set<T> visibleHexagons = new HashSet<>();
        Set<T> withinRange = grid.getHexagonsWithinRange(hexagon, range);
        for (T hex : withinRange) {
            Stream<Coordinate> line = hexagon.drawLine(hex);
            if (!line.anyMatch(c -> hexagonAttributes.blocksView(grid.getHexagon(c)))) {
                visibleHexagons.add(hex);
            }
        }
        return visibleHexagons;
    }

}

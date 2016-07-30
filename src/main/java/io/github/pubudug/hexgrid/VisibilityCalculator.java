package io.github.pubudug.hexgrid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class VisibilityCalculator<C extends Coordinate> {
    private CoordinateGrid<C> grid;
    private int range;
    private CoordinateAttributes<C> coordinateAttributes;
    private C coordinate;

    public VisibilityCalculator(C from, int range, CoordinateGrid<C> grid, CoordinateAttributes<C> attributes) {
        this.range = range;
        this.coordinate = from;
        this.grid = grid;
        this.coordinateAttributes = attributes;
    }

    public Set<C> getVisibleHexagons() {
        Set<C> visibleHexagons = new HashSet<>();
        Set<C> withinRange = grid.getCoordinatesWithinRange(coordinate, range);
        for (C hex : withinRange) {
            Stream<Stream<Coordinate>> lines = coordinate.drawLine(hex);
            if (lines.anyMatch(line -> !line.anyMatch(c -> !grid.getCoordinate(c).isPresent()
                    || coordinateAttributes.blocksView(grid.getCoordinate(c).get())))) {
                visibleHexagons.add(hex);
            }
        }
        return visibleHexagons;
    }

}

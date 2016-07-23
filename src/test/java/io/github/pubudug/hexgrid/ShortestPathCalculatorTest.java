package io.github.pubudug.hexgrid;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShortestPathCalculatorTest {

    private TerrainMap terrainMap;
    private TestHexagonFactory hexagonFactory;
    private HexagonGrid<TestHexagon> grid;

    @BeforeClass
    public void intialize() {
        this.terrainMap = new TerrainMap(new HardCodedTerrainMapReader());
        this.hexagonFactory = new TestHexagonFactory(terrainMap);
        this.grid = new HexagonGrid<TestHexagon>(hexagonFactory, terrainMap.getColumns(), terrainMap.getRows(), 30);
    }

    @Test
    public void testHumanPath() {
        ShortestPathCalculator<TestHexagon> calculator = new ShortestPathCalculator<TestHexagon>(grid,
                new HumanHexagonAttributes());
        List<TestHexagon> path = calculator.findShortestPath(grid.getHexagon(4, 6), grid.getHexagon(4, 4));
        Assert.assertEquals(path,
                Arrays.asList(Coordinate.fromOffsetCoordinates(4, 6), Coordinate.fromOffsetCoordinates(3, 5),
                        Coordinate.fromOffsetCoordinates(3, 4), Coordinate.fromOffsetCoordinates(4, 4)));
    }
}

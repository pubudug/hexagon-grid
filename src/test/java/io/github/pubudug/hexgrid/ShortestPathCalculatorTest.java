package io.github.pubudug.hexgrid;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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

    @DataProvider
    public Object[][] humanPathDataProvider() {
        // @formatter:off
        return new Object[][] { 
              { 4, 6, 4, 4, 
                  Arrays.asList(
                      Coordinate.fromOffsetCoordinates(3, 5),
                      Coordinate.fromOffsetCoordinates(3, 4), 
                      Coordinate.fromOffsetCoordinates(4, 4)) 
              },
              
              { 5, 6, 5, 4, 
                  Arrays.asList(
                      Coordinate.fromOffsetCoordinates(5, 5),
                      Coordinate.fromOffsetCoordinates(5, 4)) 
              },
              
              { 9, 4, 11, 0, 
                  Arrays.asList(
                      Coordinate.fromOffsetCoordinates(10, 4),
                      Coordinate.fromOffsetCoordinates(10, 3),
                      Coordinate.fromOffsetCoordinates(11, 2),
                      Coordinate.fromOffsetCoordinates(12, 2),
                      Coordinate.fromOffsetCoordinates(12, 1),
                      Coordinate.fromOffsetCoordinates(11, 0)),
              }, 
              
              { 2, 5, 4, 8, 
                  Arrays.asList(
                      Coordinate.fromOffsetCoordinates(3, 5),
                      Coordinate.fromOffsetCoordinates(4, 6),
                      Coordinate.fromOffsetCoordinates(4, 7),
                      Coordinate.fromOffsetCoordinates(4, 8)),
              }, 
            };
        // @formatter:on
    }

    @Test(dataProvider = "humanPathDataProvider")
    public void testHumanPath(int fromColumn, int fromRow, int toColumn, int toRow, List<Coordinate> expected) {
        ShortestPathCalculator<TestHexagon> calculator = new ShortestPathCalculator<TestHexagon>(grid,
                new HumanHexagonAttributes());
        List<TestHexagon> path = calculator.findShortestPath(grid.getHexagon(fromColumn, fromRow),
                grid.getHexagon(toColumn, toRow));
        Assert.assertEquals(path, expected);
    }
}

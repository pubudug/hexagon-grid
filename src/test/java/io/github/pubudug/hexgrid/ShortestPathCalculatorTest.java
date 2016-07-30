package io.github.pubudug.hexgrid;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ShortestPathCalculatorTest {

    private TerrainMap terrainMap;
    private CoordinateGrid<TestCoordinate> grid;
    private TestCoordinateFactory coordinateFactory;

    @BeforeClass
    public void intialize() {
        this.terrainMap = new TerrainMap(new HardCodedTerrainMapReader());
        this.coordinateFactory = new TestCoordinateFactory(terrainMap);
        this.grid = new CoordinateGrid<TestCoordinate>(terrainMap.getColumns(), terrainMap.getRows(),
                coordinateFactory);
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
                      Coordinate.fromOffsetCoordinates(3, 6),
                      Coordinate.fromOffsetCoordinates(3, 7),
                      Coordinate.fromOffsetCoordinates(4, 8)),
              }, 
            };
        // @formatter:on
    }

    @Test(dataProvider = "humanPathDataProvider")
    public void testHumanPath(int fromColumn, int fromRow, int toColumn, int toRow, List<Coordinate> expected) {
        ShortestPathCalculator<TestCoordinate> calculator = new ShortestPathCalculator<TestCoordinate>(grid,
                new HumanCoordinateAttributes<TestCoordinate>());
        List<TestCoordinate> path = calculator.findShortestPath(grid.getCoordinate(fromColumn, fromRow),
                grid.getCoordinate(toColumn, toRow));
        Assert.assertEquals(path, expected);
    }
}

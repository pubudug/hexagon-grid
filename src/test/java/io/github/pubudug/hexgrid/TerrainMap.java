package io.github.pubudug.hexgrid;

import java.util.List;

public class TerrainMap {
    private List<String> lines;

    public TerrainMap(TerrainMapReader reader) {
        this.lines = reader.readLines();
    }

    public int getRows() {
        return lines.size();
    }

    public int getColumns() {
        return lines.get(0).length();
    }

    public TerrainType getTerrain(int column, int row) {
        char typeChar = lines.get(row).charAt(column);
        return TerrainType.getByCharacter(typeChar);
    }

}

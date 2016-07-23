package io.github.pubudug.hexgrid;

import java.util.LinkedList;
import java.util.List;

public class HardCodedTerrainMapReader implements TerrainMapReader {

    @Override
    public List<String> readLines() {
        List<String> lines = new LinkedList<>();
        lines.add("TTTFFFFFWWFFFTT");
        lines.add("TTFWWWWFFFHHFFT");
        lines.add("HHFWWWWWFFFFFFF");
        lines.add("HHFWWWWWTTTTWWW");
        lines.add("HFFFFFFFFFFFFFF");
        lines.add("FFFFHHHHHHHHHHH");
        lines.add("TTTFFFFFFFHHHHH");
        lines.add("TTTTTTFFFFFHHWW");
        lines.add("TTTTFFFFFWWWWWW");
        lines.add("TTFFFFFHHWWWWWW");
        lines.add("TFFFHHHWWWWWWWW");
        return lines;
    }

}

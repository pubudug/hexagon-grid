package io.github.pubudug.hexgrid;

public enum Corner {
    RIGHT(0), NORTHEAST(5), NORTHWEST(4), LEFT(3), SOUTHWEST(2), SOUTHEAST(1);
    private int id;

    private Corner(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

package io.github.pubudug.hexgrid;

public enum Corner {
    RIGHT(0), NORTHEAST(1), NORTHWEST(2), LEFT(3), SOUTHWEST(4), SOUTHEAST(5);
    private int id;

    private Corner(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

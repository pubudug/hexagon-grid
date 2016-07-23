package io.github.pubudug.hexgrid;

import java.awt.Color;

import lombok.Getter;

@Getter
public enum TerrainType {
    TREES(Color.green), WATER(Color.blue), HILLS(Color.darkGray), FLAT(Color.gray);
    private Color color;
    private char character;

    private TerrainType(Color color) {
        this.color = color;
    }

    static TerrainType getByCharacter(char character) {
        if (character == 'T') {
            return TREES;
        } else if (character == 'W') {
            return WATER;
        } else if (character == 'H') {
            return HILLS;
        } else if (character == 'F') {
            return FLAT;
        } else {
            throw new IllegalArgumentException(character + " is not a valid terrain type");
        }
    }

}

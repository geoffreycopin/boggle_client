package game;

import errors.ParsingError;

import java.util.Arrays;

public class Grid {
    char[] grid;

    public Grid(char[] grid) {
        this.grid = grid;
    }

    public static Grid fromString(String grid) throws ParsingError {
        if (!grid.matches("[a-zA-Z]{16}")) {
            throw new ParsingError(grid + " is not a valid grid !");
        }
        return new Grid(grid.toUpperCase().toCharArray());
    }

    public String getWord(Trajectory t) {
        return Arrays.stream(t.getIndexes())
                .map((i) -> grid[i])
                .toString();
    }

    public char getLetter(int index) {
        return grid[index];
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Grid)) {
            return false;
        }
        Grid other = (Grid) obj;

        for (int i = 0; i < 16; i++) {
            if (grid[i] != other.grid[i]) {
                return false;
            }
        }

        return true;
    }
}

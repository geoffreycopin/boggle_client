package game;

import errors.ParsingError;
import javafx.util.Pair;

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

    public char[] getGrid() {
        return grid;
    }

    public char getLetter(int index) {
        return grid[index];
    }

    public char getLetter(char line, int column) {
        int index = Grid.indexOfCoordinates(line, column);
        return getLetter(index);
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

    public static Pair<Character, Integer> coordinatesOfIndex(int index) {
        Character line = letterOfLine((index / 4) + 1);
        Integer column = (index % 4) + 1;
        return new Pair<>(line, column);
    }

    public static int indexOfCoordinates(char line, int column) {
        int l = lineOfLetter(line) - 1;
        int c = column - 1;
        return (4 * l) + c;
    }

    private static Character letterOfLine(int column) {
        switch (column) {
            case 1: return 'A';
            case 2: return 'B';
            case 3: return 'C';
            case 4: return 'D';
            default: return null;
        }
    }

    private static Integer lineOfLetter(char line) {
        switch (line) {
            case 'A' : return 1;
            case 'B' : return 2;
            case 'C' : return 3;
            case 'D' : return 4;
            default: return null;
        }
    }
}

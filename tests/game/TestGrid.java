package game;

import errors.ParsingError;
import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGrid {
    @Test
    public void fromString() throws ParsingError {
        String letters = "AZERTYUIOPQSDFGH";
        Grid g = Grid.fromString(letters);
        for (int i = 0; i < 16; i++) {
            assertEquals(letters.charAt(i), g.grid[i]);
        }
    }

    @Test(expected = ParsingError.class)
    public void fromStringFailIfWrongSize() throws ParsingError {
        String letters = "AZERTYUIOPQSDFGHJKLMWXCV";
        Grid.fromString(letters);
    }

    @Test(expected = ParsingError.class)
    public void fromStringFailIfWrongCharacter() throws ParsingError {
        String letters = "AZERTYUIOPQSDFG5";
        Grid.fromString(letters);
    }

    @Test
    public void indexOfCoordinates() {
        assertEquals(0, Grid.indexOfCoordinates('A', 1));
        assertEquals(15, Grid.indexOfCoordinates('D', 4));
    }

    @Test
    public void coordinatesOfIndex() {
        assertEquals(new Pair<>('A', 1), Grid.coordinatesOfIndex(0));
        assertEquals(new Pair<>('D', 4), Grid.coordinatesOfIndex(15));
    }
}

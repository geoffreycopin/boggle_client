package game;

import errors.ParsingError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTrajectory {
    @Test
    public void fromString() throws ParsingError {
        Trajectory t = Trajectory.fromString("A1A2D4");
        Integer[] expected = {0, 1, 15};
        assertEquals(expected, t.getIndexes());
    }

    @Test(expected = ParsingError.class)
    public void fromStringFailIfToShort() throws ParsingError {
        Trajectory.fromString("A1A2");
    }

    @Test(expected = ParsingError.class)
    public void fromStringFailIfInvalidColumn() throws ParsingError {
        Trajectory.fromString("A1A2D5");
    }

    @Test(expected = ParsingError.class)
    public void fromStringFailIfInvalidLine() throws ParsingError {
        Trajectory.fromString("A1A2F4");
    }
}

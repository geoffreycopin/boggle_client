package game;

import errors.ParsingError;
import javafx.util.Pair;

import java.util.ArrayList;

public class Trajectory {
    Integer[] indexes;

    public Trajectory(Integer[] indexes) {
        this.indexes = indexes;
    }

    public static Trajectory fromString(String s) throws ParsingError {
        s = s.toUpperCase();
        if (! s.matches("([ABCD][1234]){3,16}")) {
            throw new ParsingError(s + " is not a valid Trajectory !");
        }

        ArrayList<Integer> trajectory = new ArrayList<>();
        for (int i = 0; i < s.length(); i += 2) {
            int line = (s.charAt(i) - ((int) 'A'));
            int column = Character.getNumericValue(s.charAt(i + 1));
            trajectory.add((line * 4) + (column - 1));
        }

        return new Trajectory(trajectory.toArray(new Integer[trajectory.size()]));
    }

    public Integer[] getIndexes() {
        return indexes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Integer i: getIndexes()) {
            Pair<Character, Integer> coordinates = Grid.coordinatesOfIndex(i);
            builder.append(coordinates.getKey());
            builder.append(coordinates.getValue());
        }
        return builder.toString();
    }
}

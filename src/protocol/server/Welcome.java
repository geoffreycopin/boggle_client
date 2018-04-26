package protocol.server;

import errors.ParsingError;
import game.Grid;
import game.Scores;

import java.util.HashMap;

public class Welcome extends ServerMessage {
    private int turn;
    private Grid grid;
    private HashMap<String, Integer> scores;

    public Welcome(String grid, int turn, String scores) throws ParsingError {
        this.grid = Grid.fromString(grid);
        this.turn = turn;
        this.scores = Scores.parseScores(scores);
    }

    public Grid getGrid() {
        return grid;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }
}
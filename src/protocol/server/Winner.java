package protocol.server;

import errors.ParsingError;
import game.Scores;

import java.util.HashMap;

public class Winner extends ServerMessage {
    private HashMap<String, Integer> scores;

    public Winner(String scores) throws ParsingError {
        this.scores = Scores.parseScores(scores);
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }
}

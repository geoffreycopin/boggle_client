package protocol.server;

import errors.ParsingError;
import game.Scores;

import java.util.ArrayList;
import java.util.HashMap;

public class TurnResults extends ServerMessage {
    private HashMap<String, ArrayList<String>> words;
    private HashMap<String, Integer> scores;

    public TurnResults(String words, String scores) throws ParsingError {
        this.scores = Scores.parseScores(scores);
        this.words = Scores.parsePlayedWords(this.scores.keySet(), words);
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }
}

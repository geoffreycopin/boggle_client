package protocol.server;

public class TurnResults extends ServerMessage {
    private String words;
    private String scores;

    public TurnResults(String words, String scores) {
        this.words = words;
        this.scores = scores;
    }

    public String getWords() {
        return words;
    }

    public String getScores() {
        return scores;
    }
}

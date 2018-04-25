package protocol.server;

public class Winner extends ServerMessage {
    private String scores;

    public Winner(String scores) {
        this.scores = scores;
    }

    public String getScores() {
        return scores;
    }
}

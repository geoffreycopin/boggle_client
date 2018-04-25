package protocol.server;

public class Welcome extends ServerMessage {
    private String grid;
    private String scores;

    public Welcome(String grid, String scores) {
        this.grid = grid;
        this.scores = scores;
    }

    public String getGrid() {
        return grid;
    }

    public String getScores() {
        return scores;
    }
}
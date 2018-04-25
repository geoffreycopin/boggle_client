package protocol.server;

public class TurnStart extends ServerMessage {
    private String grid;

    public TurnStart(String grid) {
        this.grid = grid;
    }

    public String getGrid() {
        return grid;
    }
}

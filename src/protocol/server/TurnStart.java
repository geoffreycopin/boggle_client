package protocol.server;

import errors.ParsingError;
import game.Grid;

public class TurnStart extends ServerMessage {
    private Grid grid;

    public TurnStart(String grid) throws ParsingError {
        this.grid = Grid.fromString(grid);
    }

    public Grid getGrid() {
        return grid;
    }
}

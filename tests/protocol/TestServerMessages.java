package protocol;

import errors.ParsingError;
import game.Grid;
import org.junit.Test;
import protocol.server.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestServerMessages {
    @Test
    public void parseWelcome() throws ParsingError {
        Welcome w = (Welcome) ServerMessage.parse("BIENVENUE/AZERTYUIOPQSDFGH/3*user1*5*user2*2/");
        Grid grid = Grid.fromString("AZERTYUIOPQSDFGH");
        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("user1", 5);
        scores.put("user2", 2);

        assertEquals(grid, w.getGrid());
        assertEquals(scores, w.getScores());
        assertEquals(3, w.getTurn());
    }

    @Test
    public void parseConnected() throws ParsingError {
        Connected c = (Connected) ServerMessage.parse("CONNECTE/userName/");
        assertEquals("userName", c.getUserName());
    }

    @Test
    public void disconnected() throws ParsingError {
        Disconnected d = (Disconnected) ServerMessage.parse("DECONNEXION/userName/");
        assertEquals("userName", d.getUserName());
    }

    @Test
    public void parseWinner() throws ParsingError {
        Winner w = (Winner) ServerMessage.parse("VAINQUEUR/user1*5*user2*6/");
        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("user1", 5);
        scores.put("user2", 6);

        assertEquals(scores, w.getScores());
    }

    @Test
    public void parseTurnStart() throws ParsingError {
        TurnStart ts = (TurnStart) ServerMessage.parse("TOUR/AZERTYUIOPQSDFGH/");
        Grid grid = Grid.fromString("AZERTYUIOPQSDFGH");

        assertEquals(grid, ts.getGrid());
    }

    @Test
    public void parseValidWord() throws ParsingError {
        ValidWord v = (ValidWord) ServerMessage.parse("MVALIDE/TRIDENT/");
        String word = "TRIDENT";
        assertEquals(word, v.getWord());
    }

    @Test
    public void parseInvalidWord() throws ParsingError {
        InvalidWord i = (InvalidWord) ServerMessage.parse("MINVALIDE/Le mot \"PREPOSTEROUS\" n'existe pas./");
        String why = "Le mot \"PREPOSTEROUS\" n'existe pas.";
        assertEquals(i.getWhy(), why);
    }

    @Test
    public void parseTurnResults() throws ParsingError {
        TurnResults tr = (TurnResults) ServerMessage.parse("BILANMOTS/user1*trident*user2*ile/user1*5*user2*2");
        HashMap<String, ArrayList<String>> words = new HashMap<>();

        ArrayList<String> w1 = new ArrayList<>();
        w1.add("trident");
        words.put("user1", w1);

        ArrayList<String> w2 = new ArrayList<>();
        w2.add("ile");
        words.put("user2", w2);

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("user1", 5);
        scores.put("user2", 2);

        assertEquals(words, tr.getWords());
        assertEquals(scores, tr.getScores());
    }
}

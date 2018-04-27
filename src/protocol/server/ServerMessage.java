package protocol.server;

import errors.ParsingError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ServerMessage {
    static final String WELCOME = "BIENVENUE/([a-zA-Z]+)/([0-9]+)\\*(.+)/";
    static final String CONNECTED = "CONNECTE/(\\w+)/";
    static final String DISCONNECTED = "DECONNEXION/(\\w+)/";
    static final String WINNER = "VAINQUEUR/(.*)/";
    static final String TURNSTART = "TOUR/(.*)/";
    static final String VALIDWORD = "MVALIDE/(.*)/";
    static final String INVALIDWORD = "MINVALIDE/(.*)/";
    static final String TURNRESULTS = "BILANMOTS/(.*)/(.*)/";

    public static ServerMessage parse(String message) throws ParsingError {
        if (message.equals("SESSION/")) {
            return new SessionStart();
        } else if (message.equals("RFIN/")) {
            return new TurnEnd();
        } else if (message.matches(WELCOME)) {
            return parseWelcome(message);
        } else if (message.matches(CONNECTED)) {
            return parseConnected(message.split("/"));
        } else if (message.matches(DISCONNECTED)) {
            return parseDisconnected(message.split("/"));
        } else if (message.matches(WINNER)) {
            return parseWinner(message.split("/"));
        } else if (message.matches(TURNSTART)) {
            return parseTurnStart(message.split("/"));
        } else if (message.matches(VALIDWORD)) {
            return parseValidWord(message.split("/"));
        } else if (message.matches(INVALIDWORD)) {
            return parseInvalidWord(message.split("/"));
        } else if (message.matches(TURNRESULTS)) {
            return parseTurnResults(message.split("/"));
        }

        throw new ParsingError(message + " is not a valid server message!");
    }

    private static Welcome parseWelcome(String request) throws ParsingError {
        Matcher m = Pattern.compile(WELCOME).matcher(request);
        m.find();
        String grid  = m.group(1);
        Integer turn = Integer.valueOf(m.group(2));
        String scores = m.group(3);
        return new Welcome(grid, turn, scores);
    }

    private static Connected parseConnected(String[] components) throws ParsingError {
        String userName = components[1];
        return new Connected(userName);
    }

    private static Disconnected parseDisconnected(String[] components) throws ParsingError {
        String userName = components[1];
        return new Disconnected(userName);
    }

    private static Winner parseWinner(String[] components) throws ParsingError {
        String scores = components[1];
        return new Winner(scores);
    }

    private static TurnStart parseTurnStart(String[] components) throws ParsingError {
        String grid = components[1];
        return new TurnStart(grid);
    }

    private static ValidWord parseValidWord(String[] components) throws ParsingError {
        String word = components[1];
        return new ValidWord(word);
    }

    private static InvalidWord parseInvalidWord(String[] components) throws ParsingError {
        String why = components[1];
        return new InvalidWord(why);
    }

    private static TurnResults parseTurnResults(String[] components) throws ParsingError {
        String words = components[1];
        String scores = components[2];
        return new TurnResults(words, scores);
    }
}

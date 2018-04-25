package protocol.server;

import errors.ParsingError;

public abstract class ServerMessage {
    public static ServerMessage parse(String message) throws ParsingError {
        if (message.isEmpty()) {
            throw new ParsingError("Empty message cannot be parsed !");
        }
        String[] components = message.split("/");
        switch (components[0]) {
            case "BIENVENUE": return parseWelcome(components);
            case "CONNECTE": return parseConnected(components);
            case "DECONNEXION": return parseDisconnected(components);
            case "SESSION": return new SessionStart();
            case "VAINQUEUR": return parseWinner(components);
            case "TOUR": return parseTurnStart(components);
            case "MVALIDE": return parseValidWord(components);
            case "MINVALIDE": return parseInvalidWord(components);
            case "RFIN": return new TurnEnd();
            case "BILANMOTS": return parseTurnResults(components);
        }

        throw new ParsingError("Invalid message !");
    }

    private static Welcome parseWelcome(String[] components) throws ParsingError {
        if (components.length < 3) {
            throw new ParsingError("Grid or scores missing !");
        }
        String grid  = components[1];
        String scores = components[2];
        return new Welcome(grid, scores);
    }

    private static Connected parseConnected(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Username missing !");
        }
        String userName = components[1];
        return new Connected(userName);
    }

    private static Disconnected parseDisconnected(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Username missing !");
        }
        String userName = components[1];
        return new Disconnected(userName);
    }

    private static Winner parseWinner(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Scores missing !");
        }
        String scores = components[1];
        return new Winner(scores);
    }

    private static TurnStart parseTurnStart(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Grid missing !");
        }
        String grid = components[1];
        return new TurnStart(grid);
    }

    private static ValidWord parseValidWord(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Word missing !");
        }
        String word = components[1];
        return new ValidWord(word);
    }

    private static InvalidWord parseInvalidWord(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Reason missing !");
        }
        String why = components[1];
        return new InvalidWord(why);
    }

    private static TurnResults parseTurnResults(String[] components) throws ParsingError {
        if (components.length < 3) {
            throw new ParsingError("Words or scores missing !");
        }
        String words = components[1];
        String scores = components[2];
        return new TurnResults(words, scores);
    }
}

package protocol.client;

import errors.ParsingError;

public abstract class ClientMessage {
    public static ClientMessage parse(String message) throws ParsingError {
        if (message.isEmpty()) {
            throw new ParsingError("Empty request cannot be parsed !");
        }
        String[] components = message.split("/");
        switch (components[0]) {
            case "CONNEXION": return parseLogin(components);
            case "SORT": return parseLogout(components);
            case "TROUVE": return parseFound(components);
        }

        throw new ParsingError("Invalid request !");
    }

    private static Login parseLogin(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Login request doesn't contain a username !");
        }
        String userName = components[1];
        return new Login(userName);
    }

    private static Logout parseLogout(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Logout request doesn't contain a username !");
        }
        String userName = components[1];
        return new Logout(userName);
    }

    private static Found parseFound(String[] components) throws ParsingError {
        if (components.length < 3) {
            throw new ParsingError("found request should contain 3 components !");
        }
        String word = components[1];
        String trajectory = components[2];
        return new Found(word, trajectory);
    }
}

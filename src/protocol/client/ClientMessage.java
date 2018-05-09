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
            case "ENVOI": return parseEnvoi(components);
            case "PENVOI": return parsePenvoi(components);
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

    private static Send parseEnvoi(String[] components) throws ParsingError {
        if (components.length < 2) {
            throw new ParsingError("Send request should contain a message !");
        }
        String message = components[1];
        return new Send(message);
    }

    private static Send parsePenvoi(String[] components) throws ParsingError {
        if (components.length < 3) {
            throw new ParsingError("private Send request should contain 3 compponents !");
        }
        String user = components[1];
        String message = components[2];
        return new Send(user, message);
    }
}

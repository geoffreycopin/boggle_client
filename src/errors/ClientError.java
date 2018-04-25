package errors;

import protocol.client.ClientMessage;

public class ClientError extends Exception {
    public ClientError(String message) {
        super(message);
    }
}

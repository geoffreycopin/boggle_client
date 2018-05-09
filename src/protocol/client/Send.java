package protocol.client;

public class Send extends ClientMessage {
    String message = "";
    String receiver = "";

    public Send(String receiver, String message) {
        this.message = message;
        this.receiver = receiver;
    }

    public Send(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        if (getReceiver().isEmpty()) {
            return String.format("ENVOI/%s/", getMessage());
        }
        return String.format("PENVOI/%s/%s/", getReceiver(), getMessage());
    }
}

package protocol.server;

public class Received extends ServerMessage {
    private String sender = "";
    private String message = "";

    public Received(String message) {
        this.message = message;
    }

    public Received(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}

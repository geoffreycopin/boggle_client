package protocol.server;

public class InvalidWord extends ServerMessage {
    private String why;

    public InvalidWord(String why) {
        this.why = why;
    }

    public String getWhy() {
        return why;
    }
}

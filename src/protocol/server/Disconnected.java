package protocol.server;

public class Disconnected extends ServerMessage {
    private String userName;

    public Disconnected(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

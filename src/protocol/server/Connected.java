package protocol.server;

public class Connected extends ServerMessage {
    private String userName;

    public Connected(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

package protocol.client;

public class Logout extends ClientMessage {
    private String userName;

    public Logout(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

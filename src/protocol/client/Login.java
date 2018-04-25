package protocol.client;

public class Login extends ClientMessage {
    private String userName;

    public Login(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String toString() {
        return String.format("CONNEXION/%s/\n", getUserName());
    }
}

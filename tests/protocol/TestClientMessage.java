package protocol;

import errors.ParsingError;
import org.junit.Test;
import protocol.client.ClientMessage;
import protocol.client.Found;
import protocol.client.Login;
import protocol.client.Logout;

import static org.junit.Assert.assertEquals;

public class TestClientMessage {
    @Test
    public void login() throws ParsingError {
        Login l = (Login) ClientMessage.parse("CONNEXION/user/");
        assertEquals("user", l.getUserName());
    }

    @Test
    public void logout() throws ParsingError {
        Logout l = (Logout) ClientMessage.parse("SORT/user/");
        assertEquals("user", l.getUserName());
    }

    @Test
    public void found() throws ParsingError {
        Found d = (Found) ClientMessage.parse("TROUVE/TRIDENT/A1A2A3/");
        assertEquals("TRIDENT", d.getWord());
        assertEquals("A1A2A3", d.getTrajectory());
    }
}

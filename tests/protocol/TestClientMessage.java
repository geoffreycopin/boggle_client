package protocol;

import errors.ParsingError;
import org.junit.Test;
import protocol.client.*;

import static org.junit.Assert.assertEquals;

public class TestClientMessage {
    @Test
    public void login() throws ParsingError {
        Login l = (Login) ClientMessage.parse("CONNEXION/user/");
        assertEquals("user", l.getUserName());
        assertEquals(l.toString(), "CONNEXION/user/");
    }

    @Test
    public void logout() throws ParsingError {
        Logout l = (Logout) ClientMessage.parse("SORT/user/");
        assertEquals("user", l.getUserName());
        assertEquals(l.toString(), "SORT/user/");
    }

    @Test
    public void found() throws ParsingError {
        Found d = (Found) ClientMessage.parse("TROUVE/TRIDENT/A1A2A3/");
        assertEquals("TRIDENT", d.getWord());
        assertEquals("A1A2A3", d.getTrajectory());
        assertEquals(d.toString(), "TROUVE/TRIDENT/A1A2A3/");
    }

    @Test
    public void send() throws ParsingError {
        Send s = (Send) ClientMessage.parse("ENVOI/Hello World/");
        assertEquals(s.getMessage(), "Hello World");
        assertEquals(s.getReceiver(), "");
        assertEquals(s.toString(), "ENVOI/Hello World/");
    }

    @Test
    public void sendPrivate() throws ParsingError {
        Send s = (Send) ClientMessage.parse("PENVOI/user1/Hello World/");
        assertEquals(s.getMessage(), "Hello World");
        assertEquals(s.getReceiver(), "user1");
        assertEquals(s.toString(), "PENVOI/user1/Hello World/");
    }
}

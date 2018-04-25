package network;

import errors.ParsingError;
import protocol.client.ClientMessage;
import protocol.server.*;

import java.io.*;
import java.net.Socket;

public class BoggleClient implements Runnable {
    private String userName;
    private BufferedReader input;
    private DataOutputStream output;
    private BoggleClientListener listener;

    public BoggleClient(String userName, String address, int port) throws IOException {
        this.userName = userName;
        Socket socket = new Socket(address, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void setListener(BoggleClientListener listener) {
        this.listener = listener;
    }

    public void listen(ServerMessage message) {
        if (listener == null) {
            return;
        }

        if (message instanceof Connected) {
            Connected c = (Connected) message;
            listener.onConnected(c.getUserName());
        } else if (message instanceof Disconnected) {
            Disconnected d = (Disconnected) message;
            listener.onDisconnected(d.getUserName());
        } else if (message instanceof Welcome) {
            Welcome w = (Welcome) message;
            listener.onWelcome(w.getGrid(), w.getScores());
        } else if (message instanceof Winner) {
            Winner w = (Winner) message;
            listener.onWinner(w.getScores());
        } else if (message instanceof TurnStart) {
            TurnStart ts = (TurnStart) message;
            listener.onTurnStart(ts.getGrid());
        } else if (message instanceof TurnEnd) {
            listener.onTurnEnd();
        } else if (message instanceof ValidWord) {
            ValidWord v = (ValidWord) message;
            listener.onValidWord(v.getWord());
        } else if (message instanceof InvalidWord) {
            InvalidWord i = (InvalidWord) message;
            listener.onInvalidWord(i.getWhy());
        } else if (message instanceof TurnResults) {
            TurnResults ts = (TurnResults) message;
            listener.onTurnResult(ts.getWords(), ts.getScores());
        }
    }

    public void makeRequest(ClientMessage request) throws IOException {
        output.writeChars(request.toString());
    }

    @Override
    public void run() {
        input.lines().forEach((l) -> {
            try {
                ServerMessage m = ServerMessage.parse(l);
                listen(m);
            } catch (ParsingError e) {
                System.err.println(e.getMessage());
            }
        });
    }
}

package network;

import errors.ParsingError;
import protocol.client.ClientMessage;
import protocol.server.*;

import java.io.*;
import java.net.Socket;

public class BoggleClient implements Runnable {
    private BufferedReader input;
    private DataOutputStream output;
    private BoggleClientListener listener;

    public BoggleClient(Socket socket) throws IOException {
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
            listener.onConnected((Connected) message);
        } else if (message instanceof Disconnected) {
            listener.onDisconnected((Disconnected) message);
        } else if (message instanceof Welcome) {
            listener.onWelcome((Welcome) message);
        } else if (message instanceof Winner) {
            listener.onWinner((Winner) message);
        } else if (message instanceof TurnStart) {
            listener.onTurnStart((TurnStart) message);
        } else if (message instanceof TurnEnd) {
            listener.onTurnEnd();
        } else if (message instanceof ValidWord) {
            listener.onValidWord((ValidWord) message);
        } else if (message instanceof InvalidWord) {
            listener.onInvalidWord((InvalidWord) message);
        } else if (message instanceof TurnResults) {
            listener.onTurnResult((TurnResults) message);
        }
    }

    public void makeRequest(ClientMessage request) throws IOException {
        output.writeBytes(request.toString());
    }

    @Override
    public void run() {
        input.lines().forEach((l) -> {
            try {
                ServerMessage m = ServerMessage.parse(l);
                listen(m);
            } catch (ParsingError e) {
                System.err.println("Error while parsing: " + l);
                System.err.println(e.getMessage());
            }
        });
    }
}

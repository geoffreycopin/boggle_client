package network;

import errors.ParsingError;
import protocol.client.ClientMessage;
import protocol.server.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class BoggleClient implements Runnable {
    private BufferedReader input;
    private DataOutputStream output;
    private BoggleClientListener listener;
    private final ArrayList<ClientMessage> pendingRequests = new ArrayList<>();
    private Thread sender;

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

    public void addRequests(ClientMessage request) {
        synchronized (pendingRequests) {
            pendingRequests.add(request);
            pendingRequests.notify();
        }
    }

    @Override
    public void run() {
        startSender();
        input.lines().forEach((l) -> {
            try {
                ServerMessage m = ServerMessage.parse(l);
                listen(m);
            } catch (ParsingError e) {
                System.err.println("Error while parsing: " + l);
                System.err.println(e.getMessage());
            }
        });
        sender.interrupt();
        listener.onTerminate();
    }

    private void startSender() {
        sender = new Thread(() -> {
            try {
                sendRequest();
            } catch (InterruptedException e) {
                // do nothing
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        sender.start();
    }

    public void sendRequest() throws InterruptedException, IOException {
        synchronized (pendingRequests) {
            while(true) {
                while (pendingRequests.isEmpty()) {
                    pendingRequests.wait();
                }
                ClientMessage request = pendingRequests.remove(pendingRequests.size() - 1);
                output.writeBytes(request.toString());
            }
        }
    }
}

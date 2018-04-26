package cli;

import errors.ParsingError;
import network.BoggleClient;
import network.BoggleClientListener;
import protocol.client.ClientMessage;
import protocol.server.*;

import java.io.IOException;
import java.util.Scanner;

public class CLIClientListener implements BoggleClientListener {
    BoggleClient client;

    public CLIClientListener(BoggleClient client) {
        this.client = client;
        client.setListener(this);
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String line = input.nextLine();
            try {
                client.makeRequest(ClientMessage.parse(line));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (ParsingError parsingError) {
                System.out.println(parsingError.getMessage());
            }
        }
    }

    @Override
    public void onWelcome(Welcome w) {
        System.out.print("\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char c =w.getGrid().getLetter((i * 4) + j);
                System.out.print(c + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        for (String player: w.getScores().keySet()) {
            System.out.println(player + ": " + w.getScores().get(player));
        }
        System.out.println("\n");
    }

    @Override
    public void onConnected(Connected c) {
        // TODO: implement
    }

    @Override
    public void onDisconnected(Disconnected d) {
        // TODO: implement
    }

    @Override
    public void onWinner(Winner w) {
        // TODO: implement
    }

    @Override
    public void onTurnStart(TurnStart t) {
        // TODO: implement
    }

    @Override
    public void onTurnEnd() {
        // TODO: implement
    }

    @Override
    public void onValidWord(ValidWord v) {
        // TODO: implement
    }

    @Override
    public void onInvalidWord(InvalidWord i) {
        // TODO: implement
    }

    @Override
    public void onTurnResult(TurnResults t) {
        // TODO: implement
    }
}

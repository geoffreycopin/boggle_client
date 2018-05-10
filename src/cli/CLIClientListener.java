package cli;

import errors.ParsingError;
import game.Grid;
import network.BoggleClient;
import network.BoggleClientListener;
import protocol.client.ClientMessage;
import protocol.server.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLIClientListener implements BoggleClientListener {
    BoggleClient client;
    int turn;

    public CLIClientListener(BoggleClient client) {
        this.client = client;
        client.setListener(this);
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String line = input.nextLine();
            try {
                client.addRequests(ClientMessage.parse(line));
            } catch (ParsingError parsingError) {
                System.out.println(parsingError.getMessage());
            }
        }
    }

    @Override
    public void onWelcome(Welcome w) {
        turn = w.getTurn();
        printGrid(w.getGrid(), w.getTurn());
        printScores(w.getScores());
    }

    @Override
    public void onConnected(Connected c) {
        System.out.println(c.getUserName() + " vient de se connecter !");
    }

    @Override
    public void onDisconnected(Disconnected d) {
        System.out.println(d.getUserName() + " vient de se déconnecter !");
    }

    @Override
    public void onWinner(Winner w) {
        System.out.println("## FIN DE LA SESSION ##");
        printScores(w.getScores());
    }

    @Override
    public void onTurnStart(TurnStart t) {
        turn++;
        System.out.println("##### DEBUT DU TOUR #####");
        printGrid(t.getGrid(), turn + 1);
    }

    @Override
    public void onTurnEnd() {
        System.out.println("##### FIN DU TOUR #####\n");
    }

    @Override
    public void onValidWord(ValidWord v) {
        System.out.println("Le mot " + v.getWord() + " a été accepté !\n");
    }

    @Override
    public void onInvalidWord(InvalidWord i) {
        System.out.println("Refus: " + i.getWhy() + "\n");
    }

    @Override
    public void onTurnResult(TurnResults t) {
        System.out.println("#### BILAN DU TOUR ####");
        printScores(t.getScores());
        printWords(t.getWords());
    }

    @Override
    public void onTerminate() {
        System.exit(0);
    }

    @Override
    public void onReceived(Received r) {
        StringBuilder message = new StringBuilder("Message");
        if (! r.getSender().isEmpty()) {
            message.append(" (");
            message.append(r.getSender());
            message.append(')');
        }
        message.append(": ");
        message.append(r.getMessage());
        System.out.println(message + "\n");
    }

    public static void printGrid(Grid grid, int turn) {
        System.out.println("######## TOUR" + turn + " ########");
        System.out.print("\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char c = grid.getLetter((i * 4) + j);
                System.out.print(c + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static void printScores(HashMap<String, Integer> scores) {
        System.out.println("######## SCORES #######");
        System.out.print("\n");
        for (String player: scores.keySet()) {
            System.out.println(player + ": " + scores.get(player));
        }
        System.out.print("\n");
    }

    private static void printWords(HashMap<String, ArrayList<String>> words) {
        System.out.println("######### MOTS ########");
        System.out.print("\n");
        for (String player: words.keySet()) {
            System.out.println(player + ": " + words.get(player));
        }
        System.out.print("\n");
    }
}

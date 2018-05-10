package bot;

import cli.CLIClientListener;
import errors.ParsingError;
import game.Grid;
import game.Trajectory;
import javafx.util.Pair;
import network.BoggleClient;
import network.BoggleClientListener;
import protocol.client.Found;
import protocol.client.Login;
import protocol.server.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AutomaticClient implements BoggleClientListener {
    private BoggleClient client;
    private List<String> allWords = new ArrayList<>();
    private final List<Trajectory> trajectories = new ArrayList<>();
    private Grid grid;
    boolean running = true;

    public AutomaticClient(BoggleClient client, String pseudo) {
        loadAllWords();
        this.client = client;
        client.setListener(this);
        client.addRequests(new Login(pseudo));
    }

    public void start() throws InterruptedException {
        Random rd = new Random();

        while (running) {
            Trajectory t;
            synchronized (trajectories) {
                while (trajectories.size() == 0) {
                    trajectories.wait();
                }
                t = trajectories.remove(trajectories.size() - 1);
            }
            client.addRequests(new Found(grid.getWord(t), t.toString()));
            Thread.sleep(rd.nextInt(10) * 1000);
        }
    }

    private void loadAllWords() {
        try {
            allWords = Files.lines(Paths.get("dico_fr.txt"))
                    .filter((w) -> w.length() >= 3)
                    .map((w) -> {
                        w = Normalizer.normalize(w, Normalizer.Form.NFD);
                        w = w.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                        return w.toUpperCase();
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.err.println("Impossible de charger le dictionnaire !\n" +
                    "L'exécutable doit être lancé dans le dossier contenant le fichier dico_fr.txt");
            System.exit(1);
        }
    }

    @Override
    public void onWelcome(Welcome w) {
        updateTrajectories(w.getGrid());
    }

    private void updateTrajectories(Grid g) {
        this.grid = g;
        CLIClientListener.printGrid(g, 0);
        buildUsableWordsList(grid);
        System.out.println("MOTS TROUVES: ");
        synchronized (trajectories) {
            for (Trajectory t : trajectories) {
                System.out.println(grid.getWord(t) +  " | " + t);
            }
            trajectories.notify();
        }
    }

    private void buildUsableWordsList(Grid g) {
        ArrayList<Trajectory> result = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                result.addAll(wordsAt(Grid.letterOfLine(i), j, "", g, new ArrayList<>(allWords)));
            }
        }

        result.sort(Comparator.comparingInt(t -> t.getIndexes().length));

        synchronized (trajectories) {
            trajectories.addAll(result);
        }
    }

    private ArrayList<Trajectory> wordsAt(char line, int row, String currentTrajectory, Grid g, ArrayList<String> dico) {
        ArrayList<Trajectory> result = new ArrayList<>();
        int idx = currentTrajectory.length() / 2;
        char letter = g.getLetter(line, row);

        ArrayList<String> filteredWords = new ArrayList<>();
        for (String w: dico) {
            if (w.length() > idx && w.charAt(idx) == letter) {
                filteredWords.add(w);
                if (w.length() == idx + 1) {
                    try {
                        result.add(Trajectory.fromString(currentTrajectory + line + String.valueOf(row)));
                    } catch (ParsingError parsingError) {
                        parsingError.printStackTrace();
                    }
                }
            }
        }

        if (filteredWords.size() == 0) {
            return result;
        }

        currentTrajectory = currentTrajectory + line + String.valueOf(row);
        for (Pair<Character, Integer> coordinates: g.coordinatesArround(line, row)) {
            if (! currentTrajectory.contains("" + coordinates.getKey() + coordinates.getValue())) {
                result.addAll(wordsAt(coordinates.getKey(), coordinates.getValue(), currentTrajectory, g, filteredWords));
            }
        }

        return result;
    }

    @Override
    public void onConnected(Connected c) {
        // DO NOTHING
    }

    @Override
    public void onDisconnected(Disconnected d) {
        // DO NOTHING
    }

    @Override
    public void onWinner(Winner w) {

    }

    @Override
    public void onTurnStart(TurnStart t) {
        updateTrajectories(t.getGrid());
    }

    @Override
    public void onTurnEnd() {
        synchronized (trajectories) {
            trajectories.clear();
        }
    }

    @Override
    public void onValidWord(ValidWord v) {
        // DO NOTHING
    }

    @Override
    public void onInvalidWord(InvalidWord i) {
        // DO NOTHING
    }

    @Override
    public void onTurnResult(TurnResults t) {
        // DO NOTHING
    }

    @Override
    public void onTerminate() {
        running = false;
    }

    @Override
    public void onReceived(Received r) {
        // DO NOTHING
    }
}

package gui;

import game.Grid;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import network.BoggleClient;
import network.BoggleClientListener;
import protocol.server.*;

import java.util.ArrayList;

public class Controller implements BoggleClientListener {
    private SimpleIntegerProperty turn = new SimpleIntegerProperty(0);
    private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty ongoingTurn = new SimpleBooleanProperty(false);
    private SimpleListProperty<Character> grid = new SimpleListProperty<>();
    private SimpleMapProperty<String, ArrayList<String>> words = new SimpleMapProperty<>();
    private SimpleMapProperty<String, Integer> scores = new SimpleMapProperty<>();

    public Controller(BoggleClient client) {
         client.setListener(this);
    }

    @Override
    public void onWelcome(Welcome w) {
        setGrid(w.getGrid());
        turn.set(w.getTurn());
        scores.setValue(FXCollections.observableMap(w.getScores()));
        connected.set(true);
        ongoingTurn.set(true);
    }

    @Override
    public void onConnected(Connected c) {
        // do nothing
    }

    @Override
    public void onDisconnected(Disconnected d) {
        // do nothing
    }

    @Override
    public void onWinner(Winner w) {
        scores.set(FXCollections.observableMap(w.getScores()));
    }

    @Override
    public void onTurnStart(TurnStart t) {
        setGrid(t.getGrid());
        turn.set(turn.get() + 1);
        ongoingTurn.set(true);
    }

    @Override
    public void onTurnEnd() {
        ongoingTurn.set(false);
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
        scores.setValue(FXCollections.observableMap(t.getScores()));
        words.setValue(FXCollections.observableMap(t.getWords()));
    }

    @Override
    public void onTerminate() {
        // TODO: implement
    }

    private void setGrid(Grid g) {
        grid.clear();
        for (char c: g.getGrid()) {
            grid.add(c);
        }
    }
}

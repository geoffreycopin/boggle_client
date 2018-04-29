package network;

import protocol.server.*;

public interface BoggleClientListener {
    void onWelcome(Welcome w);
    void onConnected(Connected c);
    void onDisconnected(Disconnected d);
    void onWinner(Winner w);
    void onTurnStart(TurnStart t);
    void onTurnEnd();
    void onValidWord(ValidWord v);
    void onInvalidWord(InvalidWord i);
    void onTurnResult(TurnResults t);
    void onTerminate();
}

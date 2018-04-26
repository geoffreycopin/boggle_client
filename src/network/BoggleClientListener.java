package network;

import protocol.server.Welcome;

public interface BoggleClientListener {
    void onWelcome(Welcome w);
    void onConnected(String userName);
    void onDisconnected(String userName);
    void onWinner(String scores);
    void onTurnStart(String grid);
    void onTurnEnd();
    void onValidWord(String word);
    void onInvalidWord(String reason);
    void onTurnResult(String words, String scores);
}

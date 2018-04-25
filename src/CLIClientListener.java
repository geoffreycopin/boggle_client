import network.BoggleClientListener;

public class CLIClientListener implements BoggleClientListener {

    @Override
    public void onWelcome(String grid, String scores) {
        // TODO: implement
    }

    @Override
    public void onConnected(String userName) {
        // TODO: implement
    }

    @Override
    public void onDisconnected(String userName) {
        // TODO: implement
    }

    @Override
    public void onWinner(String scores) {
        // TODO: implement
    }

    @Override
    public void onTurnStart(String grid) {
        // TODO: implement
    }

    @Override
    public void onTurnEnd() {
        // TODO: implement
    }

    @Override
    public void onValidWord(String word) {
        // TODO: implement
    }

    @Override
    public void onInvalidWord(String reason) {
        // TODO: implement
    }

    @Override
    public void onTurnResult(String words, String scores) {
        // TODO: implement
    }
}

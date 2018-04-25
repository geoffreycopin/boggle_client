package protocol.server;

public class ValidWord extends ServerMessage {
    private String word;

    public ValidWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}

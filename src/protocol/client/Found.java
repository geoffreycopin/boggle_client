package protocol.client;

public class Found extends ClientMessage {
    private String word;
    private String trajectory;

    public Found(String word, String trajectory) {
        this.word = word;
        this.trajectory = trajectory;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTrajectory() {
        return trajectory;
    }
}

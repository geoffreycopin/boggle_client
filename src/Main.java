import network.BoggleClient;
import network.BoggleClientListener;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BoggleClient c = new BoggleClient("geoffrey", "127.0.0.1", 2018);
        CLIClientListener cli = new CLIClientListener();
        c.setListener(cli);
        new Thread(c).start();
    }
}

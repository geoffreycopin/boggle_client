package cli;

import network.BoggleClient;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        BoggleClient c = new BoggleClient(new Socket("127.0.0.1", 2018));
        new Thread(c).start();

        CLIClientListener cli = new CLIClientListener(c);
        cli.start();
    }
}

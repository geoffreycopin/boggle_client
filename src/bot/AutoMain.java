package bot;

import network.BoggleClient;

import java.io.IOException;
import java.net.Socket;

public class AutoMain {
    public static void main(String[] args) throws IOException {
        BoggleClient c = new BoggleClient(new Socket("127.0.0.1", 2018));
        new Thread(c).start();

        AutomaticClient a = new AutomaticClient(c, "bot");
        try {
            a.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

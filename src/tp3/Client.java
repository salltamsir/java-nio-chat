package tp3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    private SocketChannel socketChannel;
    private boolean connected;

    public Client (String ip, int port) throws IOException {
        this.connected=true;
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", port));
    }


    public boolean isConnected() {
        return connected;
    }
}

package tp3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Client client = new Client("localhost",2223);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 2223));

        Thread thread = new Thread( new RepeatNetwork(socketChannel,client));
        thread.start();

    }
}
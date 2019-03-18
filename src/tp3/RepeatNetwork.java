package tp3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RepeatNetwork implements Runnable{

    private SocketChannel socketChannel;
    private ByteBuffer buffer;
    private Client client;

    public RepeatNetwork (SocketChannel socketChannel, Client client){
        buffer  = ByteBuffer.allocate(256);
    }

    @Override
    public void run() {

        while (client.isConnected()){

            try {
                socketChannel.read(buffer);
                buffer.flip();
                System.out.println(buffer.toString()+" Lu");
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

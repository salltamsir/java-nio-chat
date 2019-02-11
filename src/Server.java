import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server {
    private static final String POISON_PILL = "exit";
    Selector selector ;
    ServerSocketChannel serverSocket ;
    ByteBuffer buffer;
    Set<SocketChannel> set;
    TreeMap<String,SocketChannel> clientList;
    TreeMap<String,ArrayList<SocketChannel>> salonList;
    Handler handler;
    ChatModel chatModel;
    public Server(int port) throws IOException {

        selector = Selector.open();serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("192.168.43.68", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        buffer = ByteBuffer.allocate(256);
        set = new HashSet<>();
        clientList = new TreeMap<>();
        salonList = new TreeMap<>();
        handler = new Handler(clientList,salonList,new ChatModel(new ChatOutput()));

    }

    public void accept () throws IOException {
        while (true) {
            int test = selector.select();
            System.out.println(test+" clients");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            int  i =  1;
            while (iter.hasNext()) {
                System.out.println("Numero "+(i++));
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                if (key.isReadable()) {
                    repeat( key);
                }
                iter.remove();
            }
        }
    }

    private  void repeat(SelectionKey key) throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        buffer.flip();
        String action = new String(buffer.array()).trim();

        handler.input(Handler.decoupage (action),client,buffer);

        buffer.clear();
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
    }

    private  void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        set.add(client);

    }
    private  void register(Selector selector, ServerSocketChannel serverSocket, String name)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        clientList.put(name,client);

    }



}

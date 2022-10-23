package uz.driver.connection;

import sun.nio.cs.US_ASCII;
import uz.driver.commands.label.TSPLLabel;
import uz.driver.exceptions.ConnectionClientException;
import uz.driver.exceptions.PrinterException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static uz.driver.DriverConstant.CR_BYTES;
import static uz.driver.DriverConstant.LF_BYTES;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class EthernetConnectionClient extends AbstractConnectionClient implements TSPLConnectionClient{

    private final String host;
    private final int port;
    private ExecutorService connectionExecutorService = Executors.newSingleThreadExecutor();
    private SocketChannel channel;
    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private ByteBuffer readDataBuffer = ByteBuffer.allocate(8192);

    private Runnable connectionRunnable = new Runnable() {
        @Override
        public void run() {
            if(!isConnected){
                try{
                    selector = Selector.open();
                    channel = SocketChannel.open();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_CONNECT);
                    channel.connect(new InetSocketAddress(host, port));

                    alive = true;

                    while(alive && !Thread.interrupted()){
                        selector.select(1000);
                        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                        while(keys.hasNext()){
                            SelectionKey key = keys.next();
                            keys.remove();
                            if(!key.isValid()){
                                continue;
                            } else if(key.isConnectable()){
                                makeConnect(key);
                                isConnected = true;
                                notifyConnection();
                                channel.register(selector, SelectionKey.OP_READ);
                            } else if(key.isReadable()){
                                read(key);
                            } else {
                                System.err.println("Unrecognized " + key);
                            }
                        }
                    }

                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(isConnected){
                        notifyDisconnected();
                    }
                    isConnected = false;
                    alive = false;

                    try{
                        if(channel != null){
                            channel.close();
                        }
                        if(selector != null){
                            selector.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public EthernetConnectionClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void init() {
        System.out.println("Initialized");
    }

    @Override
    public void connect() {
        if(isConnected || alive){
            return;
        }
        connectionExecutorService.execute(connectionRunnable);
    }

    @Override
    public void disconnect() {
        if(!alive)
            return;
        alive = false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void send(String tsplMessage) {
        send(tsplMessage.getBytes(StandardCharsets.US_ASCII));
    }

    @Override
    public void send(TSPLLabel label) {
        send(label.getTsplCode());
    }

    private void send(byte[] message){
        if(!isConnected()){
            throw new PrinterException("Printer is not connected");
        }

        try{
            ByteBuffer bb = ByteBuffer.wrap(message);
            while(bb.hasRemaining()){
                channel.write(bb);
            }
            notifyMessageSent(new String(message));
        }catch(IOException e){
            notifyMessageSendFailed(new ConnectionClientException("Failed to send message"),
                    new String(message));
        }
    }

    private void makeConnect(SelectionKey key)throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        if(channel.isConnectionPending())
            channel.finishConnect();
        channel.configureBlocking(false);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        try{
            readBuffer.clear();
            int read = channel.read(readBuffer);
            readBuffer.flip();
            while (read > 0 && readBuffer.hasRemaining()){
                byte b = readBuffer.get();
                if(b != CR_BYTES[0] && b != LF_BYTES[0]){
                    readDataBuffer.put(b);
                }
                if(b == LF_BYTES[0]){
                    readDataBuffer.put((byte) '\n');
                    readDataBuffer.flip();
                    byte[] data = new byte[readDataBuffer.limit()];
                    readDataBuffer.get(data);

                    notifyMessageReceived(new String(data, US_ASCII));
                    readDataBuffer.clear();
                }
            }
            channel.register(selector, SelectionKey.OP_READ);
        }catch(IOException e){
            readDataBuffer.clear();
            readBuffer.clear();
            readBuffer.limit(0);
            key.cancel();
            channel.close();
        }
    }
}

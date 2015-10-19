package communication;

import messages.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by anders on 10/19/15.
 */
public class TelldusSocket {

    private Logger log = LogManager.getLogger(TelldusSocket.class);

    private SocketChannel socket;
    private SocketAddress sa;

    public TelldusSocket(String host, int port) throws IOException {
        sa = new InetSocketAddress(host, port);
    }

    public boolean connect() {
        if(socket != null)
            return true;

        log.debug("Connecting to "+sa+"..");
        try {
            socket = SocketChannel.open();
            socket.configureBlocking(true);
            socket.connect(sa);
            socket.finishConnect();
            log.debug("Connected");
            return true;
        }
        catch(IOException ex) {
            log.warn("Connect failed: "+ex.getMessage());
            if(log.isDebugEnabled())
                log.debug("Connection failure", ex);

            socket = null;

            return false;
        }
    }

    public void disconnect() throws IOException {
        if(socket == null)
            return;

        socket.close();
        socket = null;
    }

    public int read(ByteBuffer bb) throws IOException {
        return socket.read(bb);
    }

    public void write(Message msg) throws IOException {
        socket.write(msg.serialize());
    }

}

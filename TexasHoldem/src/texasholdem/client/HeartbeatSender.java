package texasholdem.client;

import texasholdem.TexasHoldemConstants;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Periodically sends a heartbeat to the server.
 */
public class HeartbeatSender extends Thread implements TexasHoldemConstants{

    /**
     * The id of the client
     */
    private final byte[] id;

    /**
     * Address to which heartbeats are sent
     */
    private final InetAddress address;

    /**
     * Socket on which heartbeats are sent
     */
    private final  MulticastSocket socket;

    HeartbeatSender(byte[] id, InetAddress address, MulticastSocket socket) {
        this.id = id;
        this.address = address;
        this.socket = socket;
    }

    public void run() {
        while(true) {
            DatagramPacket packet = new DatagramPacket(id, id.length,
                    address, PORT);
            try {
                socket.send(packet);
                Thread.sleep(HEARTBEAT_INTERVAL);
            }
            catch(IOException | InterruptedException ioeie) {
                ioeie.printStackTrace();
            }
        }
    }
}
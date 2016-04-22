package texasholdem.client;

import texasholdem.TexasHoldemConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Periodically sends a heartbeat to the server.
 */
class HeartbeatSender extends Thread implements TexasHoldemConstants{

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

    /**
     * true if the sender has been canceled
     */
    private volatile boolean cancel;

    /**
     * Constructs a new heartbeat sender.
     * @param id The client's id
     * @param address The destination address
     * @param socket The socket over which to send the heartbeat
     */
    HeartbeatSender(byte[] id, InetAddress address, MulticastSocket socket) {
        this.id = id;
        this.address = address;
        this.socket = socket;
        cancel = false;
    }

    /**
     * Periodically sends a heartbeat to the server.
     */
    @Override
    public void run() {
        while(!cancel) {
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

    /**
     * Cancels the listener, causing the {@link #run()} method to stop.
     */
    public void cancel() {
        cancel = true;
    }
}
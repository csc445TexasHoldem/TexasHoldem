package texasholdem.client;

import texasholdem.Heartbeat;
import texasholdem.SharedUtilities;
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
        DatagramPacket hbPacket = null;
        try {
            byte[] hbBytes = SharedUtilities.toByteArray(new Heartbeat(id));
            hbPacket = new DatagramPacket(hbBytes, hbBytes.length, address,
                    PORT);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        while(!cancel) {
            try {
                socket.send(hbPacket);
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
    void cancel() {
        cancel = true;
    }
}
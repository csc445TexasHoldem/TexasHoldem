package texasholdem.server;

import texasholdem.Heartbeat;
import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiHeartbeatSender extends Thread 
implements TexasHoldemConstants {
    /**
     * Address to which heartbeats are sent
     */
    private final InetAddress address;

    /**
     * Socket on which heartbeats are sent
     */
    private final  MulticastSocket socket;

    /**
     * Constructs server multicast heartbeat sender.
     * @param address The multicast group address
     * @param socket The socket over which to send the heartbeat
     */
    MultiHeartbeatSender(InetAddress address, MulticastSocket socket) {
        this.address = address;
        this.socket = socket;
    }

    /**
     * Periodically sends a heartbeat to the server.
     */
    @Override
    public void run() {
        DatagramPacket hbPacket = null;
        try {
            byte[] hbBytes = SharedUtilities.toByteArray(new Heartbeat());
            hbPacket = new DatagramPacket(hbBytes, hbBytes.length, address,
                    PORT);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        while(true) {
            try {
                socket.send(hbPacket);
                Thread.sleep(HEARTBEAT_INTERVAL);
            }
            catch(IOException | InterruptedException ioeie) {
                ioeie.printStackTrace();
            }
        }
    }
}

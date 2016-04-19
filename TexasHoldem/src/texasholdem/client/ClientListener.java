package texasholdem.client;

import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

/**
 * Listens for incoming datagrams and forwards them to the client.
 */
class ClientListener extends Thread implements TexasHoldemConstants {

    /**
     * The multicast socket to listen on
     */
    private MulticastSocket mSocket;

    /**
     * The associated client
     */
    private Client client;

    /**
     * Constructs a new client listener.
     * @param client The associated client
     * @param mSocket The multicast socket to listen on
     */
    ClientListener(Client client, MulticastSocket mSocket) {
        this.mSocket = mSocket;
        this.client = client;
    }

    /**
     * Listens for incoming datagrams and forwards them to the client.
     */
    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKET_SIZE],
                MAX_PACKET_SIZE);
        while(true) {
            try {
                mSocket.receive(packet);
                client.receiveObject(SharedUtilities.toObject(packet.getData()));
            }
            catch(SocketTimeoutException ste) {
                // Socket has timed out, indicating disconnection from server
                // How is this handled?
                ste.printStackTrace();
            }
            catch(IOException | ClassNotFoundException ioecnfe) {
                ioecnfe.printStackTrace();
                System.exit(1);
            }
        }
    }
}
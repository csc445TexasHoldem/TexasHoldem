package texasholdem.client;

import texasholdem.TexasHoldemConstants;
import texasholdem.gamestate.GameState;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Client
 */
public class Client implements TexasHoldemConstants {
    /**
     * Separate thread to listen for incoming packets
     */
    private ClientListener listener;

    /**
     * Multicast address
     */
    private InetAddress address;

    /**
     * Multicast socket
     */
    private MulticastSocket mSocket;

    /**
     * The most current game state this client has received
     */
    private GameState gameState;

    /**
     * Constructs a client in the Texas Hold 'em game.
     */
    public Client() {
        try {
            address = InetAddress.getByName(MULTICAST_ADDRESS);
            mSocket = new MulticastSocket(PORT);
            mSocket.joinGroup(address);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }

        listener = new ClientListener(this, mSocket);
        listener.start();
    }

    /**
     * Invoked when the listener thread receives a serialized object from the
     * server.
     * @param obj The object received
     */
    void receiveObject(Object obj) {
        if(obj == null) {
            throw new NullPointerException("Null received.");
        }
        if(obj instanceof GameState) {

        }
    }
}
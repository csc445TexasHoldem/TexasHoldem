package texasholdem.client;

import texasholdem.Heartbeat;
import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;
import texasholdem.gamestate.GameState;
import texasholdem.gamestate.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * GameClient. Includes a separate listener thread to receive incoming
 * packets and forward them to this class, and a separate heartbeat thread to
 * periodically send a heartbeat to the server.
 */
public class GameClient implements TexasHoldemConstants {
    /**
     * Separate thread to listen for incoming packets
     */
    private ClientListener listener;

    /**
     * Multicast address
     */
    private InetAddress multiAddress;

    /**
     * Server's address
     */
    private InetAddress serverAddress;

    /**
     * Multicast socket
     */
    private MulticastSocket mSocket;

    /**
     * The most current game state this client has received
     */
    private GameState gameState;

    /**
     * The Player object representing this client's player
     */
    private Player player;

    /**
     * Spearate thread to periodically send a heartbeat to the server
     */
    private HeartbeatSender sender;

    /**
     * Constructs a client in the Texas Hold 'em game.
     */
    public GameClient() {
        try {
            multiAddress = InetAddress.getByName(MULTICAST_ADDRESS);
            serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            mSocket = new MulticastSocket(PORT);
            mSocket.joinGroup(multiAddress);
            player = new Player(mSocket.getNetworkInterface()
                    .getHardwareAddress());
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }

        listener = new ClientListener(this, mSocket);
        listener.start();

        sender = new HeartbeatSender(id(), serverAddress, mSocket);
        sender.start();
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
        else if(obj instanceof GameState) {
            // Do stuff
        }
        else if(obj instanceof Heartbeat) {
            // Reply with a new heartbeat
            try {
                byte[] hbBytes =
                        SharedUtilities.toByteArray(new Heartbeat(id()));
                DatagramPacket packet = new DatagramPacket(hbBytes,
                        hbBytes.length, serverAddress, PORT);
                mSocket.send(packet);
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Returns the player's unique id.
     * @return The player's id
     */
    public byte[] id() {
        return player.id();
    }

    /**
     * Cancels the client, shutting down the heartbeat and the packet listener.
     */
    public void cancel() {
        listener.cancel();
        sender.cancel();
    }
}
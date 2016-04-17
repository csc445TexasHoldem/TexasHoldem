package texasholdem;

/**
 * Constants used by the game.
 */
public interface TexasHoldemConstants {

    /**
     * Server's url
     */
    String SERVER_URL = "pi.oswego.edu";

    /**
     * Port number
     */
    int PORT = 2714;

    /**
     * Multicast address used by the clients and server
     */
    String MULTICAST_ADDRESS = "";

    /**
     * Maximum size of a packet sent by any node
     */
    int MAX_PACKET_SIZE = 1500;
}
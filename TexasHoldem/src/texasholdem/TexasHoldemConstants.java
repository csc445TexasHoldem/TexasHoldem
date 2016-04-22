package texasholdem;

/**
 * Constants used by the game.
 */
public interface TexasHoldemConstants {

    /**
     * Port number
     */
    int PORT = 2714;

    /**
     * Multicast address used by the clients and server
     */
    String MULTICAST_ADDRESS = "225.145.14.36";

    /**
     * Notes:   First byte of an IPv6 multicast address is all 1s.
     *          Second byte consists of 4 flag bits and 4 scope bits
     *              flag:
     *                  bit 0: reserved
     *                  bit 1: 0 = Rendezvous point not embedded
     *                  bit 2: 0 = Without prefix information, 1 = Address
     *                      based on network prefix
     *                  bit 3: 0 = Well-known multicast address, 1 = Dynamically
     *                      assigned multicast address
     *              scope: e = global scope
     *          Third byte consists of 4 RES (reserved?) bits and 4 RIID
     *                  (used only in conjunction with RP field) bits
     *          Fourth byte is the PLEN (all 1s)
     *          Next 8 bytes are newtwork prefix
     *            Final 4 bytes are group ID
     * pi=129.3.20.26
     * home=71.115.167.57
     */
    byte[] MULTICAST_BYTE_ADDRESS = { -1, 62, 0, -1, 0, 0, 0, 0, -127, 3, 20,
                                      26, 15, 15, 15, 15 };

    /**
     * Server's IP address
     */
    String SERVER_ADDRESS = "pi.oswego.edu";

    /**
     * Maximum size of a packet sent by any node
     */
    int MAX_PACKET_SIZE = 1500;

    /**
     * Interval for sending heartbeats to server
     */
    int HEARTBEAT_INTERVAL = 1000;
}
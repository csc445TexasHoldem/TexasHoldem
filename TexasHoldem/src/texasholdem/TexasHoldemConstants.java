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
package texasholdem.client;

import texasholdem.TexasHoldemConstants;
import texasholdem.gamestate.GameState;
import texasholdem.gamestate.Player;

import java.io.IOException;
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
   private InetAddress group;

   /**
    * Server's address
    */
   private InetAddress server;

   /**
    * Multicast socket
    */
   private MulticastSocket socket;

   /**
    * The most current game state this client has received
    */
   private GameState gameState;

   /**
    * The Player object representing this client's player
    */
   private Player player;

   /**
    * Constructs a client in the Texas Hold 'em game.
    */
   public GameClient() {
      try {
         group = InetAddress.getByName(MULTICAST_ADDRESS);
         server = InetAddress.getByName(SERVER_ADDRESS);
         socket = new MulticastSocket(PORT);
         socket.joinGroup(group);
         player = new Player(socket.getNetworkInterface()
               .getHardwareAddress());
      }
      catch(IOException ioe) {
         ioe.printStackTrace();
         System.exit(1);
      }

      // Set up listener for incoming packets
      listener = new ClientListener(this, socket, server);
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
      else if(obj instanceof GameState) {
         // Do stuff
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
    * Cancels the client, shutting down packet listener.
    */
   public void cancel() {
      listener.cancel();
   }
}
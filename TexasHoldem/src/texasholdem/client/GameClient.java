package texasholdem.client;

import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;
import texasholdem.gamestate.GameState;
import texasholdem.gamestate.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

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
    * Reads user input
    */
   private Scanner in;

   /**
    * Timeout before the client assumes that the server is no longer connected
    */
   private final static int DROP_TIMEOUT = 5 * HEARTBEAT_INTERVAL;

   /**
    * true iff the player is the OP and is in the pregame mode
    */
   private volatile boolean pregame;

   /**
    * The player's name
    */
   private String name;

   /**
    * Constructs a client in the Texas Hold 'em game.
    */
   public GameClient() {
      in = new Scanner(System.in);
      System.out.println("Enter your name: ");
      name = in.nextLine();
      pregame = false;
      try {
         group = InetAddress.getByName(MULTICAST_ADDRESS);
         server = InetAddress.getByName(SERVER_ADDRESS);
         socket = new MulticastSocket(PORT);
         socket.joinGroup(group);
         socket.setSoTimeout(DROP_TIMEOUT);
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
      else if(obj instanceof Player) {
         // This should only occur when the player first joins the game
         player = (Player)obj;
         player.setName(name);
         try {
            player.setId(socket.getNetworkInterface().getHardwareAddress());
         }
         catch(SocketException se) {
            se.printStackTrace();
            System.out.println("Socket error.");
            System.exit(1);
         }
         // Return the player to the server
         try {
            byte[] playerBytes = SharedUtilities.toByteArray(player);
            DatagramPacket packet = new DatagramPacket(playerBytes,
                  playerBytes.length, server, PORT);
            socket.send(packet);
         }
         catch(IOException ioe) {
            ioe.printStackTrace();
         }
      }
      else if(obj instanceof GameState) {
         // Do stuff
      }
   }

   /**
    * Returns the player's unique id.
    * @return The player's id
    */
   public byte[] getId() {
      return player.getId();
   }

   /**
    * Cancels the client, shutting down packet listener.
    */
   public void cancel() {
      listener.cancel();
   }
}

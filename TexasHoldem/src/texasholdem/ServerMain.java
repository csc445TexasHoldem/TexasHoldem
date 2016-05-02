package texasholdem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import texasholdem.server.GameServer;

/**
 * Main class for server
 */
public class ServerMain implements TexasHoldemConstants {
   /**
    * This is the port# that the game servers start at
    */
   private static final int GAME_PORT = 2730;
   
   /**
    * This is the array of port#'s we will theoretically
    * run multiple games on
    */
   private static int[] PORTS = new int[50];
   
   /**
    * This is the array that determines if the particular 
    * port# is in use
    */
   private static boolean[] IN_USE = new boolean[50];
   
   /**
    * This is the socket that clients send to the begin a 
    * game
    */
   private static DatagramSocket INITIALIZE_GAME;
   
   /** 
    * This is the array of sockets that each game can be
    * run on
    */
   private static MulticastSocket[] GAME_SOCKETS;
   
   /**
    * This is an array of actual games that are running
    */
   private static GameServer[] GAMES = new GameServer[50];
   
   /**
    * DatagramPacket to receive requests to start games
    */
   private static DatagramPacket incoming;
   
   /**
    * Keeps track of the lowest port# available to run
    * a game on
    */
   private static int NEXT = 0;
   
   /**
    * Fill the port#s from 230 - 279 auto and
    * set all used ports to false
    */
   private static void populatePorts() {
      for (int i = 0; i < PORTS.length; i++) {
         PORTS[i] = GAME_PORT+i;
         IN_USE[i] = false;
      }
   }
   
   public static void main(String[] args)  {
      populatePorts();
      try {
         incoming = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);
         INITIALIZE_GAME = new DatagramSocket(PORT);
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }
      for(;;) {
         try {
            INITIALIZE_GAME.receive(incoming);
            /**
             * Receives a request to play a game
             * if the incoming packet is indeed a request to play
             * the ServerMain creates a multicast socket and 
             * creates a new GameServer using the socket created
             */
            if (new String(incoming.getData()).equalsIgnoreCase("play"/*something*/)) { 
               GAME_SOCKETS[NEXT] = new MulticastSocket(PORTS[NEXT]);
               GAMES[NEXT] = new GameServer(GAME_SOCKETS[NEXT], incoming.getAddress().getAddress());
               IN_USE[NEXT] = true;
            }
         } catch (IOException ioe) {
            ioe.printStackTrace();
         }
      }
   }
}
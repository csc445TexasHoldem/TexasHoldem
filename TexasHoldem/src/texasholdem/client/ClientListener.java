package texasholdem.client;

import texasholdem.Heartbeat;
import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

/**
 * Listens for incoming datagrams and forwards them to the client.
 */
class ClientListener extends Thread implements TexasHoldemConstants {

   /**
    * The multicast socket to listen on
    */
   private MulticastSocket socket;

   /**
    * The associated client
    */
   private GameClient client;

   /**
    * true if the listener has been canceled
    */
   private volatile boolean cancel;

    /**
     * The server's address
     */
   private InetAddress server;

   /**
    * Constructs a new client listener.
    * @param client The associated client
    * @param socket The multicast socket to listen on
    * @param server The server's address
    */
   ClientListener(GameClient client, MulticastSocket socket, InetAddress
         server) {
      this.socket = socket;
      this.client = client;
      cancel = false;
      this.server = server;
   }

   /**
    * Listens for incoming datagrams and either responds with a
    * heartbeat or forwards them to the client.
    */
   @Override
   public void run() {
      DatagramPacket packetIn = new DatagramPacket(new byte[MAX_PACKET_SIZE],
            MAX_PACKET_SIZE);
      while(!cancel) {
         try {
            socket.receive(packetIn);
            Object obj = SharedUtilities.toObject(packetIn.getData());
            if(obj instanceof Heartbeat) {
               // Send heartbeat to server
               byte[] hbBytes =
                     SharedUtilities.toByteArray(new Heartbeat(client.getId()));
               socket.send(new DatagramPacket(hbBytes, hbBytes.length,
                     server, PORT));
            }
            else {
               client.receiveObject(obj);
            }
         }
         catch(SocketTimeoutException ste) {
            disconnected();
         }
         catch(IOException | ClassNotFoundException ioecnfe) {
            ioecnfe.printStackTrace();
            System.exit(1);
         }
      }
   }

   /**
    * Cancels the listener, causing the {@link #run()} method to stop.
    */
   void cancel() {
      cancel = true;
   }

   /**
    * Invoked when the listener detects that it has been disconnected from
    * the server.
    */
   private void disconnected() {
      client.cancel();
      System.out.println("Disconnected from server.");
   }
}
}

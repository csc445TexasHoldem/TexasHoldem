package texasholdem.server;

import java.io.*;
import java.net.*;
import texasholdem.TexasHoldemConstants;
import texasholdem.SharedUtilities;

public class GameServer implements TexasHoldemConstants {

   /**
    * The server's id
    */
   private byte[] id;

   public static void main(String[] args) {
      MulticastSocket socket;
      Object request;
      byte[] recPacket = new byte[MAX_PACKET_SIZE];
      byte[] sendPacket;
      DatagramPacket packet = new DatagramPacket(recPacket, recPacket.length);
      InetAddress address;
      String instructions = "Send your MAC address";
      while (true) {
         try {
            socket = new MulticastSocket(PORT);
            while (true) {
               socket.receive(packet);
               request = SharedUtilities.toObject(packet.getData());
               address = packet.getAddress();
               sendPacket = SharedUtilities.toByteArray(instructions);
               if (request == "sick indicator to start game") {
                  packet = new DatagramPacket(sendPacket, sendPacket.length,
                                              address, PORT);
                  socket.send(packet);
                  new GameServerThread(socket).start();
                  new MultiHeartbeatSender(address, socket).start();
               }
               socket.close();
            }
         } catch (IOException ex) {
            ex.printStackTrace();
         } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
         }
      }
   }
}

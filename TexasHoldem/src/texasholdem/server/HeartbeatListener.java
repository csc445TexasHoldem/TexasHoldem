package texasholdem.server;

import texasholdem.SharedUtilities;
import texasholdem.TexasHoldemConstants;
import texasholdem.Heartbeat;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/*
 * Not sure how we want to store all of the players' id. Either in 
 * an array or and array list, and then to keep track of when they 
 * all last responded to the heartbeat.
 */

public class HeartbeatListener extends Thread implements TexasHoldemConstants {
   private int counter = 0;
   private ArrayList<Heartbeat> heartbeats = new ArrayList<>();;
         byte[] id;
   private final MulticastSocket mSocket;
   private volatile boolean dead;
   
   HeartbeatListener(byte[] id, MulticastSocket socket) {
      this.mSocket = socket;
      heartbeats.add(new Heartbeat(id));
   }
   
   public void addHeartbeat(byte[] id) {
      heartbeats.add(new Heartbeat(id));
   }
   /* This part needs to be modified to fit ScheduledThreadPoolExecutor */
   @Override
   public void run() {
      DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKET_SIZE],
               MAX_PACKET_SIZE);
      while(!dead) {
         try {
            mSocket.setSoTimeout(HEARTBEAT_INTERVAL);
            mSocket.receive(packet);
            
         } catch (SocketTimeoutException ste) {
            counter++;
            if (counter == 5) {
               dead = true;
            }
            /* could send this to everyone instead of printing on server*/
            System.out.println("Client " + id + " not responding: " +
                  ((5*HEARTBEAT_INTERVAL)-(counter*HEARTBEAT_INTERVAL))
                  + "ms until dropped");
         } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
         }
      }
   }
   /* This part needs to be modified to fit ScheduledThreadPoolExecutor */
}

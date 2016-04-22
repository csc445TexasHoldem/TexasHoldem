package texasholdem.server;

import java.io.*;
import java.net.*;

/*
pi.cs.oswego.edu:
    inet addr:129.3.20.26
 */
public class GameServer implements Serializable {

    static private InetAddress[] players = new InetAddress[8];
    static private MulticastSocket socket = null;
    /* or whatever port we wanna use */
    static final int PORT = 2693;
    /* this is pi's address */
    static final String IPV4 = "129:3:20:26";

    public static void main(String[] args) {
        byte[] packSize = new byte[512];
        String packData;
        boolean gameStart = false, gameEnd = false;
        for (;;) {
            try {

                socket = new MulticastSocket(PORT);
                DatagramPacket rec = new DatagramPacket(packSize, packSize.length);
                while (players[0] == null) {
                    socket.receive(rec);
                    packData = new String(rec.getData());
                    if (packData.equals("lets play")) {
                        players[0] = rec.getAddress();
                    }
                }
                /* something to set up a lobby */
                while (!gameStart) {
                    socket.receive(rec);
                    if (rec.getAddress() == players[0]) {
                        if (new String(rec.getData()).equals("start")) {
                            gameStart = true;
                        } else {
                            /*
                           players[0] is sending a heartbeat
                             */
                        }
                    } else {
                        for (int i = 0; i < players.length; i++) {
                            if (rec.getAddress() != players[i]) {
                                players[i] = rec.getAddress();
                                break;
                            } else {
                                /*
                                players[i] is sending a heartbeat
                                 */
                            }
                        }
                    }
                }
                while (!gameEnd) {
                    /* Execute game */
                    /* deal hands and take bets */
                    /* receive input from clients, wait for player 'up' to take
                    turn */
                    /* update all clients about what occured from player's turn */
                    /* progress the game */
                    /* if the round is up, declare who won */

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

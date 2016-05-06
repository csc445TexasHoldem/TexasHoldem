package texasholdem;

import java.io.Serializable;

/**
 * Acknowledgement of receipt of the gamestate object by a client. Sent from
 * client to server only.
 */
public class GameStateAck implements Serializable{

   /**
    * The sequence number of the gamestate being acknowledged
    */
   private final int sequenceNumber;

   /**
    * The id of the player sending the ACK
    */
   private final long sender;

   /**
    * Constructs a gamestate ACK for the gamestate with the specified
    * sequence number.
    * @param sequenceNumber The sequence number of the gamestate being
    *        acknowledged
    * @param sender The id of the player sending the ACK
    */
   public GameStateAck(int sequenceNumber, long sender) {
      this.sequenceNumber = sequenceNumber;
      this.sender = sender;
   }
}

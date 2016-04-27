package texasholdem;

import java.io.Serializable;

/**
 * Heartbeat object sent to maintain membership in the game
 */
public class Heartbeat implements Serializable {

   /**
    * The id of the sender
    */
   private final byte[] sender;

   /**
    * Constructs a heartbeat.
    * @param sender The sender's id
    */
   public Heartbeat(byte[] sender) {
      this.sender = sender;
   }

   /**
    * Returns the id of the heartbeat's sender.
    * @return
    */
   public byte[] getSender() {
      return sender;
   }
}

package texasholdem;

import java.io.Serializable;

/**
 * Heartbeat object sent to maintain membership in the game
 */
public class Heartbeat implements Serializable {

   private final byte[] sender;

   public Heartbeat(byte[] sender) {
      this.sender = sender;
   }

   public byte[] getSender() {
      return sender;
   }
}
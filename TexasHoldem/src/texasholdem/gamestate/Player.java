package texasholdem.gamestate;

import java.io.Serializable;

/**
 * A player in the game.
 */
public class Player implements Serializable {

   /**
    * The player's id
    */
   private final byte[] id;

   /**
    * Constructs a new player with the specified id.
    * @param id The player's id
    */
   public Player(byte[] id) {
      this.id = id;
   }

   /**
    * Returns the player's unique id.
    * @return The player's id
    */
   public byte[] id() {
      return id;
   }
}
package texasholdem.gamestate;

import java.io.Serializable;

/**
 * A player in the game.
 */
public class Player implements Serializable {

   /**
    * The player's id
    */
   private byte[] id;

   /**
    * The player's username
    */
   private String name;

   /**
    * Returns the player's unique id.
    * @return The player's id
    */
   public byte[] getId() {
      return id;
   }

   /**
    * Sets the player's id.
    * @param id The new id
    */
   public void setId(byte[] id) {
      this.id = id;
   }

   /**
    * Returns the player's username.
    * @return The player's name
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the player's name.
    * @param name The player's new name
    */
   public void setName(String name) {
      this.name = name;
   }
}

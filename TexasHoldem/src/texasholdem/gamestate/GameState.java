package texasholdem.gamestate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Current complete state of the game.
 */
public class GameState implements Serializable {
    private ArrayList<Player> players;
    private int currentBet;
    private int currentPlayer;

    /**
     * Creates a new game state.
     */
    public GameState() {
        players = new ArrayList<>();
        currentBet = 0;
        currentPlayer = 0;
    }

    /**
     * Attempts to add a player to the game.
     * @param player The player to be added
     * @return true if the player is successfully added, false otherwise
     */
    public boolean addPlayer(Player player) {
        if(players.contains(player)) {
            System.err.println("Player already in game.");
            return false;
        }
        players.add(player);
        return true;
    }
}
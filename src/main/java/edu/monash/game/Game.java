package edu.monash.game;

import edu.monash.game.actions.Action;
import edu.monash.game.player.PlacePhase;
import edu.monash.game.player.Player;

import java.util.Stack;

public class Game {

    private Board board;
    private Player player1, player2, currentPlayer;
    private int turnCount;
    private Stack<Move> movesPlayed;

    public Game() {
        initializeNewGame();
    }

    public Board getBoard() {
        return board;
    }

    public boolean execute(Action action) {
        if (action.isValid(this, currentPlayer)) {
            action.executeOn(this);
            switchPlayer();
            return true;
        }
        return false;
    }

    public void storePlayedMove(Move move) {
        movesPlayed.push(move);
    }

    public void initializeNewGame() {
        board = new Board();

        player1 = new Player(Piece.BLACK, PlacePhase::new);
        player2 = new Player(Piece.WHITE, PlacePhase::new);
        currentPlayer = player1;

        movesPlayed = new Stack<>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        turnCount++;
    }

    public boolean isRunning() {
        return player1.hasLost() || player2.hasLost();
    }

}

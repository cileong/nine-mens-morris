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

    public void executeAction(Action action) {
        if (action.isValid(this, currentPlayer))
            action.execute(this);
    }

    public void storePlayedMove(Move move) {
        movesPlayed.push(move);
    }

    public void initializeNewGame() {
        // Initialize board.
        board = new Board();

        // Initialize players.
        player1 = new Player();
        player2 = new Player();
        player1.setPhase(new PlacePhase(player1));
        player2.setPhase(new PlacePhase(player2));
        currentPlayer = player1;

        // Host a stack for storing the moves played.
        movesPlayed = new Stack<>();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turnCount++;
    }

}

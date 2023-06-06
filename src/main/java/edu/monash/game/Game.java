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

            return true;
        }
        return false;
    }

    public void storePlayedMove(Move move) {
        if (move != null)
            movesPlayed.push(move);
    }

    public Stack<Move> getPlayedMove() {
        return movesPlayed;
    }

    public void initializeNewGame() {
        board = new Board();

        player1 = new Player(PieceColour.BLACK, PlacePhase::new);
        player2 = new Player(PieceColour.WHITE, PlacePhase::new);
        currentPlayer = player1;

        turnCount = 0;

        movesPlayed = new Stack<>();
    }

    public Player getPlayer() {
        return currentPlayer;
    }

    public Player getOpponent() {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    public void switchActivePlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        turnCount++;
    }

}

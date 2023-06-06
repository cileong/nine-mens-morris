package edu.monash.game;

import edu.monash.game.actions.Action;
import edu.monash.game.player.PlacePhase;
import edu.monash.game.player.Player;

import java.util.Stack;

public class Game {

    private Board board;
    private Player player1, player2, currentPlayer;
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

        movesPlayed = new Stack<>();
    }

    public void loadGameState(GameState gameState) {
        // Reset the game.
        initializeNewGame();

        Stack<Move> moves = gameState.getMoves();
        Move[] movesArray = moves.toArray(new Move[0]);

        if (movesArray.length == 0)
            return;

        // Fast-forward the game to the last move.
        for (Move move : movesArray) {
            move.executeOn(board);
            storePlayedMove(move);

            Player player = getPlayer(move.pieceColour());

            if (move.from() == null) {
                player.decrementPiecesOnHand();
                player.incrementPiecesOnBoard();
            }
            if (move.to() == null) {
                player.decrementPiecesOnBoard();
            }

            // Perform phase transitioning.
            getPlayer().attemptTransitionPhase();
            getOpponent().attemptTransitionPhase();
        }

        Move lastMove = movesArray[movesArray.length - 1];
        Player player = getPlayer(lastMove.pieceColour());

        // Last move is remove
        if (lastMove.to() == null)
            currentPlayer = player;
        // Last move is place or move
        else {
            Position destination = board.getPosition(lastMove.to());
            player.setHasFormedMill(destination.isInMill());
            currentPlayer = player;
            if (!player.hasFormedMill())
                switchActivePlayer();
        }

        movesPlayed = moves;
    }

    public Player getPlayer() {
        return currentPlayer;
    }

    private Player getPlayer(PieceColour colour) {
        return player1.getPieceColour() == colour ? player1 : player2;
    }

    public Player getOpponent() {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    private Player getOpponent(PieceColour colour) {
        return player1.getPieceColour() == colour ? player2 : player1;
    }

    public void switchActivePlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    public Stack<Move> getMoves() {
        return movesPlayed;
    }

}

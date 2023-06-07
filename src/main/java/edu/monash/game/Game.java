package edu.monash.game;

import edu.monash.game.actions.Action;
import edu.monash.game.player.PlacePhase;
import edu.monash.game.player.Player;

import java.util.Stack;

/**
 * The game class.
 */
public class Game {

    /**
     * The game board.
     */
    private Board board;
    /**
     * The players.
     */
    private Player player1, player2, currentPlayer;
    /**
     * The moves played.
     */
    private Stack<Move> movesPlayed;

    public Game() {
        initializeNewGame();
    }

    /**
     * Gets the current board state.
     *
     * @return The board.
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Execute the action on the game.
     *
     * @param action The action to execute.
     * @return True if the action was executed, false otherwise.
     */
    public boolean execute(Action action) {
        if (action.isValid(this, currentPlayer)) {
            action.executeOn(this);

            return true;
        }
        return false;
    }

    /**
     * Store the move into a stack of moves played.
     *
     * @param move The move to store.
     */
    public void storePlayedMove(Move move) {
        if (move != null)
            movesPlayed.push(move);
    }

    /**
     * Gets the stack of played move.
     *
     * @return The stack of played move.
     */
    public Stack<Move> getPlayedMove() {
        return movesPlayed;
    }

    /**
     * Resets the game state to a new game's state.
     */
    public void initializeNewGame() {
        board = new Board();

        player1 = new Player(PieceColour.BLACK, PlacePhase::new);
        player2 = new Player(PieceColour.WHITE, PlacePhase::new);
        currentPlayer = player1;

        movesPlayed = new Stack<>();
    }

    /**
     * Switches the active player.
     * @param gameState The game state to switch the active player on.
     */
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
                getOpponent(move.pieceColour()).decrementPiecesOnBoard();
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

    /**
     * Gets the active player.
     * @return The active player.
     */
    public Player getPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the player of the given piece colour.
     * @param colour The piece colour of the player.
     * @return The player of the given piece colour.
     */
    public Player getPlayer(PieceColour colour) {
        return player1.getPieceColour() == colour ? player1 : player2;
    }

    /**
     * Gets the opponent of the active player.
     * @return The opponent of the active player.
     */
    public Player getOpponent() {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    /**
     * Gets the opponent of the given piece colour.
     * @param colour The colour of the player.
     * @return The opponent of the given piece colour.
     */
    private Player getOpponent(PieceColour colour) {
        return player1.getPieceColour() == colour ? player2 : player1;
    }

    /**
     * Switches the active player.
     */
    public void switchActivePlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    /**
     * Gets the stack of moves played.
     * @return The stack of moves played.
     */
    public Stack<Move> getMoves() {
        return movesPlayed;
    }

}

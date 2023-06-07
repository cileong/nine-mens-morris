package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Stack;

/**
 * A class representing the state of a game.
 */
@JsonIgnoreProperties(value = { "board" }, allowGetters = true)
public class GameState {
    private Board board;
    private Stack<Move> moves;

    /**
     * Creates a new game state.
     *
     * @param board The current state of the board.
     * @param moves The stack of moves played.
     */
    @JsonCreator
    public GameState(@JsonProperty("board") Board board,
                     @JsonProperty("moves") Stack<Move> moves) {
        this.board = board;
        this.moves = moves;
    }

    /**
     * Gets the board.
     *
     * @return The board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Gets the moves.
     *
     * @return The stack of moves played.
     */
    public Stack<Move> getMoves() {
        return this.moves;
    }

    /**
     * Sets the board.
     *
     * @param board The board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the moves.
     *
     * @param moves The stack of moves.
     */
    public void setMoves(Stack<Move> moves) {
        this.moves = moves;
    }

}

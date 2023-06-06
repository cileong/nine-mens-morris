package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Stack;

@JsonIgnoreProperties(value = { "board" }, allowGetters = true)
public class GameState {

    private Board board;
    private Stack<Move> moves;

    @JsonCreator
    public GameState(@JsonProperty("board") Board board,
                     @JsonProperty("moves") Stack<Move> moves) {
        this.board = board;
        this.moves = moves;
    }

    public Board getBoard() {
        return this.board;
    }

    public Stack<Move> getMoves() {
        return this.moves;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMoves(Stack<Move> moves) {
        this.moves = moves;
    }

}

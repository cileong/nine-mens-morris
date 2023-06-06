package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Move;

import java.util.Stack;

class GameState {

    private Board board;
    private Stack<Move> moves;

    GameState(Board board, Stack<Move> moves) {
        this.board = board;
        this.moves = moves;
    }

    public Board getBoard() {
        return this.board;
    }

    public Stack<Move> getMoves() {
        return this.moves;
    }

    // setters
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMoves(Stack<Move> moves) {
        this.moves = moves;
    }

}

package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.player.Player;

public class UndoAction implements Action {
    private final Move move;
    private Integer from;
    private Integer to;

    private boolean isRemoveAction;

    public UndoAction(Game game) {
        Move lastMove = game.getPlayedMove().pop();
        game.switchActivePlayer();
        this.from = lastMove.to();
        this.to = lastMove.from();
        this.move = new Move(game.getPlayer().getPieceColour(), this.from, this.to);
    }

    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());

        if (game.getOpponent().hasFormedMill()) {
            game.switchActivePlayer();
            game.getPlayer().setHasFormedMill(false);
        }
        if (move.from() ==  null) {
            game.getOpponent().incrementPiecesOnBoard();
            game.getPlayer().setHasFormedMill(true);
            game.getBoard().getPosition(move.to()).setPiece(game.getOpponent().getPieceColour());
        } else if (move.to() ==  null) {
            game.getPlayer().decrementPiecesOnBoard();
            game.getPlayer().incrementPiecesOnHand();
        }

        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

}

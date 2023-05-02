package edu.monash.game;

import edu.monash.game.player.Player;

public class Position {
    private static int idCounter = 0;
    private final int id;
    private Position neighbourUp, neighbourDown, neighbourLeft, neighbourRight;
    private Piece occupiedBy;

    public Position() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public Position withUpNeighbour(Position neighbourUp) {
        this.neighbourUp = neighbourUp;
        return this;
    }

    public Position withDownNeighbour(Position neighbourDown) {
        this.neighbourDown = neighbourDown;
        return this;
    }

    public Position withLeftNeighbour(Position neighbourLeft) {
        this.neighbourLeft = neighbourLeft;
        return this;
    }

    public Position withRightNeighbour(Position neighbourRight) {
        this.neighbourRight = neighbourRight;
        return this;
    }

    public boolean canPieceBePlaced() {
        return occupiedBy == null;
    }

    public boolean canPieceBeRemoved(Player player) {
        if (occupiedBy == player.getPieceColour())
            return true;
        else
            return occupiedBy != null &&
                    !isInHorizontalMill() &&
                    !isInVerticalMill();
    }

    public Piece getPiece() {
        return occupiedBy;
    }

    public void setPiece(Piece piece) {
        occupiedBy = piece;
    }

    public boolean isInHorizontalMill() {
        if (isHorizontalAnchor()) {
            return neighbourLeft.occupiedBy == occupiedBy &&
                    neighbourRight.occupiedBy == occupiedBy;
        } else {
            return (neighbourLeft != null && neighbourLeft.isInHorizontalMill()) ||
                    neighbourRight.isInHorizontalMill();
        }
    }

    public boolean isInVerticalMill() {
        if (isVerticalAnchor()) {
            return neighbourUp.occupiedBy == occupiedBy &&
                    neighbourDown.occupiedBy == occupiedBy;
        } else {
            assert neighbourUp != null || neighbourDown != null;
            return (neighbourUp != null && neighbourUp.isInVerticalMill()) ||
                    neighbourDown.isInVerticalMill();
        }
    }

    private boolean isHorizontalAnchor() {
        return neighbourLeft != null && neighbourRight != null;
    }

    private boolean isVerticalAnchor() {
        return neighbourUp != null && neighbourDown != null;
    }

}

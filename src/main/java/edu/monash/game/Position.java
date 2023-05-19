package edu.monash.game;

import edu.monash.game.player.Player;

public class Position {
    private static int idCounter = 0;
    private final int id;
    private Position neighbourUp, neighbourDown, neighbourLeft, neighbourRight;
    private PieceColour occupiedBy;

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

    public boolean isNeighbourTo(Position other) {
        return (neighbourUp != null && neighbourUp.equals(other)) ||
                (neighbourDown != null && neighbourDown.equals(other)) ||
                (neighbourLeft != null && neighbourLeft.equals(other)) ||
                (neighbourRight != null && neighbourRight.equals(other));
    }

    public boolean canPieceBePlaced(Player player) {
        return occupiedBy == null;
    }

    public boolean canPieceBeMoved(Player player) {
        return occupiedBy == player.getPieceColour();
    }

    public boolean canPieceBeRemoved(Player player) {
        return occupiedBy != null &&
                occupiedBy != player.getPieceColour() &&
                !isInHorizontalMill() &&
                !isInVerticalMill();
    }

    public PieceColour getPiece() {
        return occupiedBy;
    }

    public void setPiece(PieceColour pieceColour) {
        occupiedBy = pieceColour;
    }

    public boolean isInHorizontalMill() {
        if (isHorizontalAnchor()) {
            return neighbourLeft.occupiedBy == occupiedBy &&
                    neighbourRight.occupiedBy == occupiedBy;
        } else {
            return (neighbourLeft != null && neighbourLeft.isInHorizontalMill()) ||
                    (neighbourRight != null && neighbourRight.isInHorizontalMill());
        }
    }

    public boolean isInVerticalMill() {
        if (isVerticalAnchor()) {
            return neighbourUp.occupiedBy == occupiedBy &&
                    neighbourDown.occupiedBy == occupiedBy;
        } else {
            return (neighbourUp != null && neighbourUp.isInVerticalMill()) ||
                    (neighbourDown != null && neighbourDown.isInVerticalMill());
        }
    }

    private boolean isHorizontalAnchor() {
        return neighbourLeft != null && neighbourRight != null;
    }

    private boolean isVerticalAnchor() {
        return neighbourUp != null && neighbourDown != null;
    }

}

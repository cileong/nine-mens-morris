package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.monash.game.player.Player;

public class Position {

    /**
     * The id of the position.
     */
    private final int id;

    /**
     * The neighbours of the position.
     */
    private Position neighbourUp, neighbourDown, neighbourLeft, neighbourRight;

    /**
     * The piece on the position.
     */
    private PieceColour occupiedBy;

    public Position(int id) {
        this.id = id;
    }

    @JsonCreator
    public Position(@JsonProperty("id") int id,
                    @JsonProperty("piece") PieceColour occupiedBy) {
        this.id = id;
        this.occupiedBy = occupiedBy;
    }

    /**
     * Gets the ID of the position.
     * @return The ID of the position.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the neighbour above the position.
     * @param neighbourUp The neighbour above the position.
     * @return The position itself.
     */
    public Position withUpNeighbour(Position neighbourUp) {
        this.neighbourUp = neighbourUp;
        return this;
    }

    /**
     * Sets the neighbour below the position.
     * @param neighbourDown The neighbour below the position.
     * @return The position itself.
     */
    public Position withDownNeighbour(Position neighbourDown) {
        this.neighbourDown = neighbourDown;
        return this;
    }

    /**
     * Sets the neighbour to the left of the position.
     * @param neighbourLeft The neighbour to the left of the position.
     * @return The position itself.
     */
    public Position withLeftNeighbour(Position neighbourLeft) {
        this.neighbourLeft = neighbourLeft;
        return this;
    }

    /**
     * Sets the neighbour to the right of the position.
     * @param neighbourRight The neighbour to the right of the position.
     * @return The position itself.
     */
    public Position withRightNeighbour(Position neighbourRight) {
        this.neighbourRight = neighbourRight;
        return this;
    }

    /**
     * Given another position, checks if it is a neighbour to the position.
     * @param other The other position.
     * @return True if the other position is a neighbour to the position, false otherwise.
     */
    public boolean isNeighbourTo(Position other) {
        return (neighbourUp != null && neighbourUp.equals(other)) ||
                (neighbourDown != null && neighbourDown.equals(other)) ||
                (neighbourLeft != null && neighbourLeft.equals(other)) ||
                (neighbourRight != null && neighbourRight.equals(other));
    }

    /**
     * Tests whether a piece can be placed on the position by a player.
     * @param player The player.
     * @return True if a piece can be placed on the position by the player, false otherwise.
     */
    public boolean canPieceBePlaced(Player player) {
        return occupiedBy == null;
    }

    /**
     * Tests whether a piece can be moved from the position by a player.
     * @param player The player.
     * @return True if a piece can be moved from the position by the player, false otherwise.
     */
    public boolean canPieceBeMoved(Player player) {
        return occupiedBy == player.getPieceColour();
    }

    /**
     * Tests whether a piece can be removed from the position by a player.
     * @param player The player.
     * @return True if a piece can be removed from the position by the player, false otherwise.
     */
    public boolean canPieceBeRemoved(Player player) {
        return occupiedBy != null &&
                occupiedBy != player.getPieceColour() &&
                !isInHorizontalMill() &&
                !isInVerticalMill();
    }

    /**
     * Returns the colour of the piece on the position.
     * @return The colour of the piece on the position.
     */
    public PieceColour getPiece() {
        return occupiedBy;
    }

    /**
     * Sets the piece on the position with its piece colour.
     * @param pieceColour The piece colour.
     */
    public void setPiece(PieceColour pieceColour) {
        occupiedBy = pieceColour;
    }

    /**
     * Tests whether the position is part of a mill.
     * @return True if the position is part of a mill, false otherwise.
     */
    @JsonIgnore
    public boolean isInMill() {
        return isInHorizontalMill() || isInVerticalMill();
    }

    /**
     * Tests whether the position is part of a horizontal mill.
     * @return True if the position is part of a horizontal mill, false otherwise.
     */
    @JsonIgnore
    public boolean isInHorizontalMill() {
        if (isHorizontalAnchor()) {
            return neighbourLeft.occupiedBy == occupiedBy &&
                    neighbourRight.occupiedBy == occupiedBy;
        } else {
            return (neighbourLeft != null && neighbourLeft.isInHorizontalMill()) ||
                    (neighbourRight != null && neighbourRight.isInHorizontalMill());
        }
    }

    /**
     * Tests whether the position is part of a vertical mill.
     * @return True if the position is part of a vertical mill, false otherwise.
     */
    @JsonIgnore
    public boolean isInVerticalMill() {
        if (isVerticalAnchor()) {
            return neighbourUp.occupiedBy == occupiedBy &&
                    neighbourDown.occupiedBy == occupiedBy;
        } else {
            return (neighbourUp != null && neighbourUp.isInVerticalMill()) ||
                    (neighbourDown != null && neighbourDown.isInVerticalMill());
        }
    }

    /**
     * Checks if the position is an anchor in a horizontal mill.
     * @return True if the position is an anchor in a horizontal mill, false otherwise.
     */
    private boolean isHorizontalAnchor() {
        return id % 2 == 1;
    }

    /**
     * Checks if the position is an anchor in a vertical mill.
     * @return True if the position is an anchor in a vertical mill, false otherwise.
     */
    private boolean isVerticalAnchor() {
        return neighbourUp != null && neighbourDown != null;
    }

    /**
     * String representation of the position.
     * @return String representation of the position.
     */
    @Override
    public String toString() {
        return String.format("Position left: " + neighbourLeft.getId() + " Position right: " + neighbourRight.getId());
    }

}

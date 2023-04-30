package src;

public class Position {

    private int x,y;

    private Piece occupiedBy;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return occupiedBy;
    }

    public void setPiece(Piece piece) {
        this.occupiedBy = piece;
    }

    public boolean isVacant() {
        return occupiedBy == null;
    }

}

class NewPosition {

    private static int idCounter = 0;
    private final int id;
    private NewPosition neighbourUp, neighbourDown, neighbourLeft, neighbourRight;
    private Piece occupiedBy;

    public NewPosition() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public NewPosition withUpNeighbour(NewPosition neighbourUp) {
        this.neighbourUp = neighbourUp;
        return this;
    }

    public NewPosition withDownNeighbour(NewPosition neighbourDown) {
        this.neighbourDown = neighbourDown;
        return this;
    }

    public NewPosition withLeftNeighbour(NewPosition neighbourLeft) {
        this.neighbourLeft = neighbourLeft;
        return this;
    }

    public NewPosition withRightNeighbour(NewPosition neighbourRight) {
        this.neighbourRight = neighbourRight;
        return this;
    }

    public boolean canPieceBePlaced() {
        return occupiedBy == null;
    }

    public boolean canPieceBeRemoved() {
        return occupiedBy != null &&
                !isInHorizontalMill() &&
                !isInVerticalMill();
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


package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class representing the game board.
 */
public class Board {

    /**
     * The number of rings on the board.
     */
    private static final int NUM_RINGS = 3;

    /**
     * The number of positions in each ring.
     */
    private static final int NUM_POSITIONS_PER_RING = 8;

    /**
     * The positions on the board.
     */
    private final List<Position> positions;


    public Board() {
        positions = createBoardStructure();
    }

    /**
     * Creates a board from a list of positions.
     *
     * @param positions The positions.
     */
    @JsonCreator
    public Board(@JsonProperty("positions") List<Position> positions) {
        Position[][] rings = new Position[NUM_RINGS][NUM_POSITIONS_PER_RING];
        for (Position position : positions) {
            int ring = position.getId() / NUM_POSITIONS_PER_RING;
            int positionInRing = position.getId() % NUM_POSITIONS_PER_RING;
            rings[ring][positionInRing] = position;
        }
        linkPositionNeighbors(rings);
        this.positions = positions;
    }

    /**
     * Create the board structure on the board.
     *
     * @return The list of positions.
     */
    private List<Position> createBoardStructure() {
        // Create the positions.
        Position[][] rings = createPositions();

        // Link the positions.
        linkPositionNeighbors(rings);

        return Arrays.stream(rings)
                .flatMap(Arrays::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Creates the positions on the board.
     *
     * @return The positions.
     */
    private Position[][] createPositions() {
        return IntStream.range(0, NUM_RINGS)
                .mapToObj(y -> IntStream.range(0, NUM_POSITIONS_PER_RING)
                        .mapToObj(x -> new Position((8 * y) + x))
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);
    }

    /**
     * Link the neighbours of each position.
     *
     * @param rings The list of positions.
     */
    private void linkPositionNeighbors(Position[][] rings) {
        for (int y = 0; y < NUM_RINGS; y++) {
            for (int x = 0; x < NUM_POSITIONS_PER_RING; x++) {
                rings[y][x] = rings[y][x]
                        .withLeftNeighbour(rings[y][previousOfX(x)])
                        .withRightNeighbour(rings[y][nextOfX(x)])
                        .withUpNeighbour(x % 2 == 1 ? rings[previousOfY(y)][x] : null)
                        .withDownNeighbour(x % 2 == 1 ? rings[nextOfY(y)][x] : null);
            }
        }
    }


    /**
     * Gets the positions on the board with the given position id.
     *
     * @return The positions.
     */
    public Position getPosition(Integer id) {
        if (id == null)
            return null;

        return positions.stream()
                .filter(position -> position.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the previous y position id.
     *
     * @return The position id.
     */
    private int previousOfY(int y) {
        int index = (y - 1) % NUM_RINGS;
        return toPositiveIndex(index, NUM_RINGS);
    }

    /**
     * Gets the next y position id.
     *
     * @return The position id.
     */
    private int nextOfY(int y) {
        return (y + 1) % NUM_RINGS;
    }

    /**
     * Gets the previous x position id.
     *
     * @return The position id.
     */
    private int previousOfX(int x) {
        int index = (x - 1) % NUM_POSITIONS_PER_RING;
        return toPositiveIndex(index, NUM_POSITIONS_PER_RING);
    }

    /**
     * Gets the next x position id.
     *
     * @return The position id.
     */
    private int nextOfX(int x) {
        return (x + 1) % NUM_POSITIONS_PER_RING;
    }

    /**
     * Gets the positive index of a position.
     *
     * @param index  The index.
     * @param length The length of the list.
     * @return The positive index.
     */
    private int toPositiveIndex(int index, int length) {
        return (length + index) % length;
    }

    /**
     * List out the current state of the board.
     *
     * @return The current board state
     */
    @Override
    public String toString() {
        // Get the occupiedBy of all positions in the positions array
        List<PieceColour> occupiedBy = positions.stream()
                .map(Position::getPiece)
                .toList();
        return String.format("Board{positions=%s}", occupiedBy);
    }

    /**
     * Check if the board has no valid move.
     * @return true if the board has no more valid move, false otherwise.
     */
    public boolean hasNoValidMove() {
        return positions.stream().allMatch(
                position ->
                        position.canPieceBePlaced(null) ||
                        position.isInHorizontalMill() ||
                        position.isInVerticalMill());
    }
}

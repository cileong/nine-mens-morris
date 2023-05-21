package edu.monash.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private static final int NUM_RINGS = 3;
    private static final int NUM_POSITIONS_PER_RING = 8;

    private final List<Position> positions;


    public Board() {
        positions = createBoardStructure();
    }

    private List<Position> createBoardStructure() {
        Position[][] rings = IntStream.range(0, NUM_RINGS)
                .mapToObj(y -> IntStream.range(0, NUM_POSITIONS_PER_RING)
                        .mapToObj(x -> new Position((8*y)+x))
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);

        for (int y = 0; y < NUM_RINGS; y++) {
            for (int x = 0; x < NUM_POSITIONS_PER_RING; x++) {
                rings[y][x] = rings[y][x]
                        .withLeftNeighbour(rings[y][previousOfX(x)])
                        .withRightNeighbour(rings[y][nextOfX(x)])
                        .withUpNeighbour(x % 2 == 1 ? rings[previousOfY(y)][x] : null)
                        .withDownNeighbour(x % 2 == 1 ? rings[nextOfY(y)][x] : null);
            }
        }

        return Arrays.stream(rings)
                .flatMap(Arrays::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Position getPosition(Integer id) {
        if (id == null)
            return null;

        return positions.stream()
                .filter(position -> position.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int previousOfY(int y) {
        int index = (y - 1) % NUM_RINGS;
        return toPositiveIndex(index, NUM_RINGS);
    }

    private int nextOfY(int y) {
        return (y + 1) % NUM_RINGS;
    }

    private int previousOfX(int x) {
        int index = (x - 1) % NUM_POSITIONS_PER_RING;
        return toPositiveIndex(index, NUM_POSITIONS_PER_RING);
    }

    private int nextOfX(int x) {
        return (x + 1) % NUM_POSITIONS_PER_RING;
    }

    private int toPositiveIndex(int index, int length) {
        return (length + index) % length;
    }

    @Override
    public String toString() {
        // Get the occupiedBy of all positions in the positions array
        List<PieceColour> occupiedBy = positions.stream()
                .map(Position::getPiece)
                .toList();
        return String.format("Board{positions=%s}", occupiedBy);
    }
}

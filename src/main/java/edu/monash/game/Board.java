package edu.monash.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private static Board instance = new Board();
    private static final int NUM_RINGS = 3;
    private static final int NUM_POSITIONS_PER_RING = 8;

    List<Position> positions;
    PlayerPhase p1Phase, p2Phase;

    public Board() {
        positions = createBoardStructure();

        p1Phase = new PlacePhase(this);
        p2Phase = new PlacePhase(this);
    }

    public static Board getInstance() {
        return instance;
    }

    private List<Position> createBoardStructure() {
        Position[][] rings = IntStream.range(0, NUM_RINGS)
                .mapToObj(x -> IntStream.range(0, NUM_POSITIONS_PER_RING)
                        .mapToObj(y -> new Position())
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);

        for (int x = 0; x < NUM_RINGS; x++) {
            for (int y = 0; y < NUM_POSITIONS_PER_RING; y++) {
                rings[x][y] = rings[x][y]
                        .withLeftNeighbour(rings[x][previousOfY(y)])
                        .withRightNeighbour(rings[x][nextOfY(y)])
                        .withUpNeighbour(y % 2 == 1 ? rings[previousOfX(x)][y] : null)
                        .withDownNeighbour(y % 2 == 1 ? rings[nextOfX(x)][y] : null);
            }
        }

        return Arrays.stream(rings)
                .flatMap(Arrays::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int previousOfY(int y) {
        int index = (y - 1) % NUM_POSITIONS_PER_RING;
        return toPositiveIndex(index, NUM_POSITIONS_PER_RING);
    }

    private int nextOfY(int y) {
        return (y + 1) % NUM_POSITIONS_PER_RING;
    }

    private int previousOfX(int x) {
        int index = (x - 1) % NUM_RINGS;
        return toPositiveIndex(index, NUM_RINGS);
    }

    private int nextOfX(int x) {
        return (x + 1) % NUM_RINGS;
    }

    private int toPositiveIndex(int index, int length) {
        return (length + index) % length;
    }

}

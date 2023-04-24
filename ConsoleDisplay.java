import java.util.Arrays;

public class ConsoleDisplay extends Display {

    private final String emptyBoardString = """
            A-----------B-----------C
            |           |           |
            |   D-------E-------F   |
            |   |       |       |   |
            |   |   G---H---I   |   |
            |   |   |       |   |   |
            J---K---L       M---N---O
            |   |   |       |   |   |
            |   |   P---Q---R   |   |
            |   |       |       |   |
            |   S-------T-------U   |
            |           |           |
            V-----------W-----------X
            """;
    public ConsoleDisplay(Game game) {
        super(game);
    }

    /**
     * The game calls this method to update the display when the game state changes.
     */
    @Override
    public void update() {
        char[][] emptyBoard = Arrays.stream(emptyBoardString.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        for (char[] row : emptyBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println("");
        }

//        Board = game.getBoard();
//        Position position;
//        for (int y=0; y<3; y++) {
//            for (int x=0; x<3; x++) {
//                position = board[y][x];
//                if (position == Position.EMPTY) {
//                    System.out.print(" ");
//                } else if (position == Position.X) {
//                    System.out.print("X");
//                } else if (position == Position.O) {
//                    System.out.print("O");
//                }
//            }
//            System.out.println();
//        }
    }

}

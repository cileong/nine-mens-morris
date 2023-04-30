package src;

import src.displays.Display;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private List<Move> moves;
    private List<Display> displays;

    public Game() {
        board = new Board();
        moves = new ArrayList<>();
        displays = new ArrayList<>();
    }

    public void addDisplay(Display display) {
        displays.add(display);
    }

    public void removeDisplay(Display display) {
        displays.remove(display);
    }

    private void updateDisplays() {
        for (Display display : displays) {
            display.update();
        }
    }

    public void run() {

    }

}

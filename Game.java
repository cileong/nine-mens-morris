import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<Display> displays;

    public Game() {
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

package src;

import src.controllers.ConsoleController;
import src.controllers.Controller;
import src.displays.ConsoleDisplay;
import src.displays.Display;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        Display display = new ConsoleDisplay(game);
        Controller controller = new ConsoleController(game, display);

        game.addDisplay(display);

        game.run();
    }

}

import java.util.Scanner;

public class ConsoleController extends Controller {

    // Controller instantiate a Menu object,
    // the Menu object knows pressing what keys will trigger what actions.
    // The Display will know how to display it to the screen.


    private Scanner scanner;

    public ConsoleController(Game game, Display display) {
        super(game, display);
        scanner = new Scanner(System.in);

        // Setup keyboard listeners, when event fires,
        // create appropriate action class, send to Game instance,
        // game.execute(action).
    }

    // Display has a method showMenu(Menu menu).
    // Display menu by calling display.showMenu(menu).

}

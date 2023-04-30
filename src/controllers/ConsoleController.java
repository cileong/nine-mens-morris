package src.controllers;

import src.Game;
import src.displays.Display;

import java.util.Scanner;

public class ConsoleController extends Controller {

    // src.controllers.Controller instantiate a Menu object,
    // the Menu object knows pressing what keys will trigger what actions.
    // The src.displays.Display will know how to display it to the screen.


    private Scanner scanner;

    public ConsoleController(Game game, Display display) {
        super(game, display);
        scanner = new Scanner(System.in);

        // Setup keyboard listeners, when event fires,
        // create appropriate action class, send to src.Game instance,
        // game.execute(action).
    }

    // src.displays.Display has a method showMenu(Menu menu).
    // src.displays.Display menu by calling display.showMenu(menu).

}

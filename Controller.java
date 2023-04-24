public abstract class Controller {

    protected Game game;
    protected Display display;

    public Controller(Game game, Display display) {
        this.game = game;
        this.display = display;
    }

}

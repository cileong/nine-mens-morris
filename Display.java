public abstract class Display {

    protected Game game;

    public Display(Game game) {
        this.game = game;
    }

    public abstract void update();

}

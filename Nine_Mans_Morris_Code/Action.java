package Nine_Mans_Morris_Code;

public abstract class Action {
    private Game game;

    public String getNextAction() {
        return toString();
    }

    public abstract Player execute(Player player, Board board);

}

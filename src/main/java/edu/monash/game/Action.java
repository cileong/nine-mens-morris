package edu.monash.game;

public abstract class Action {
    private Game game;

    public String getNextAction() {
        return toString();
    }

    public abstract Player execute(Player player, Board board);

}

package edu.monash.game;

import java.util.ArrayList;

public class SaveAction extends Action {
    private ArrayList<Move> moves;

    public Boolean save() {
        return false;
    }

    public int size() {
        return 0;
    }

    @Override
    public Player execute(Player player, Board board) {
        return null;
    }
}

package edu.monash.game;

import java.util.ArrayList;

public class LoadAction extends Action {

    public Boolean clear() {
        return false;
    }

    public ArrayList<Action> getUnmodifiableMoveList() {
        return null;
    }

    @Override
    public Player execute(Player player, Board board) {
        return null;
    }
}

package edu.monash;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

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

package Nine_Mans_Morris_Code;

import java.util.ArrayList;

public class Piece {
    private char displayChar;
    private ArrayList<Action> allowableActions;

    public char getDisplayChar() {
        return displayChar;
    }

    public void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }

    public ArrayList<Action> getAllowableActions() {
        return allowableActions;
    }

    public void setAllowableActions(ArrayList<Action> allowableActions) {
        this.allowableActions = allowableActions;
    }


}

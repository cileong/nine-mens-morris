package edu.monash.game;

public class Player {
    private String name;
    private int numOfPieces;
    PlayerPhase p1Phase, p2Phase;

    public Player (){
        p1Phase = new PlacePhase(this);
        p2Phase = new PlacePhase(this);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    public int getPiecesPlaced() {
        return numOfPieces;
    }

}

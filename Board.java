public class Board {

    private PlayerPhase player1Phase, player2Phase;

    public Board() {
        player1Phase = new PlacePhase(this);
        player2Phase = new PlacePhase(this);
    }

    void setPlayer1Phase(PlayerPhase player1Phase) {
        this.player1Phase = player1Phase;
    }

    void setPlayer2Phase(PlayerPhase player2Phase) {
        this.player2Phase = player2Phase;
    }

}

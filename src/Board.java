public class Board {
    private Token[][] state;

    public Board() {
        this.state = new Token[][] {{Token(" ")}};
    }

    public Board(Token[][] state) {
        this.state = state;
    }
}

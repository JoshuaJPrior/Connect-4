public class Board {
    private Token[][] state;

    public Board() {
        this.state = new Token[][]{{new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")},
                {new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")},
                {new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")},
                {new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")},
                {new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")},
                {new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" "), new Token(" ")}};
    }

    public Board(Token[][] state) {
        this.state = state;
    }

    public Token[][] getState() {
        return state;
    }

    public void setState(Token[][] state) {
        this.state = state;
    }

    public String getIndividualToken(int x, int y) {
        return state[x][y].representation;
    }

    public void setIndividualToken(int x, int y, Token token) {
        this.state[x][y] = token;
    }

    public int getRowOfLastMove(int column) {
        for (int row = 0; row < 6; row++) {
            if (!getIndividualToken(row, column).equals(" ")) {
                return row;
            }
        }
        return -1;
    }
    
    public Boolean isValidMove(int column) {
        if (this.state[0][column].representation.equals(" ")) {
            return true;
        }
        return this.state[getRowOfLastMove(column - 1)][column].representation.equals(" ");
    }

    public Board makeMove(Token token, int column) {
        try {
            if (!this.isValidMove(column)) {
                throw new Exception("Exception message");
            }
            Board newBoard = new Board();
            newBoard.setState(this.state);
            if (newBoard.getIndividualToken(5, column).equals(" ")) {
                newBoard.setIndividualToken(5, column, token);
            } else {
                for (int row = 0; row < 6; row++) {

                    if (!getIndividualToken(row, column).equals(" ")) {
                        setIndividualToken(row - 1, column, token);
                        break;
                    }
                }
            }
            return newBoard;
        } catch (Exception e) {
            return null;
        }
    }

    public void printBoard() {
        Token[][] gameState = getState();
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                if (gameState[row][column].representation.equals(" ")) {
                    System.out.print("\"\"" + " ");
                } else {
                    System.out.print(gameState[row][column].representation + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

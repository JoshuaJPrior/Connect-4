import java.util.Scanner;

public class Connect4 {
    final int ROW_COUNT = 6;
    final int COL_COUNT = 7;

    public static void main(String[] args) {
        // TODO code application logic here
        Player player1 = new Player("Joshua", new Token("r"));
        Player player2 = new Player("Computer", new Token("y"));
        Game newGame = new Game(player1, player2);
        GameStatus gameStatus = GameStatus.PLAYING;
        while (newGame.totalMoves < 42) {
            Scanner newScanner = new Scanner(System.in);
            int inputCol;
            do {
                System.out.println("Enter a column number (1-7):");
                inputCol = newScanner.nextInt();
            } while (!newGame.currentBoard.isValidMove(inputCol - 1));
            newGame.makeMove(newGame.players[0], inputCol - 1);
            newGame.currentBoard.printBoard();
            gameStatus = newGame.determineGameStatus(player1.token, inputCol);
            if (gameStatus == GameStatus.WON) {
                System.out.println("You win!");
                break;
            } else if (gameStatus == GameStatus.DRAWN) {
                break;
            }
            int computerCol = newGame.determineComputerMove(inputCol);
            System.out.println("Computer chose column: " + computerCol);
            newGame.makeMove(newGame.players[1], computerCol - 1);
            newGame.currentBoard.printBoard();
            gameStatus = newGame.determineGameStatus(player2.token, computerCol);
            if (gameStatus == GameStatus.WON) {
                System.out.println("You Lost!");
                break;
            }
        }
        if (gameStatus == GameStatus.DRAWN) {
            System.out.println("Draw!");
        }
    }
}

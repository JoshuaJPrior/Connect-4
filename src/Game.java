import java.awt.*;
import java.util.*;

enum GameStatus {
    PLAYING, DRAWN, WON
}

public class Game {
    public Board currentBoard;
    public int totalMoves;
    public Player[] players;
    public int hoverX;
    public Boolean finishedComputerMove = true;

    public Game(Player player1, Player player2) {
        this.currentBoard = new Board();
        this.totalMoves = 0;
        this.players = new Player[]{player1, player2};
    }

    public Game(Game gameToBeCopied) {
        Token[][] newTokenArray = new Token[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                newTokenArray[row][col] = gameToBeCopied.currentBoard.getState()[row][col];
            }
        }
        this.currentBoard = new Board(newTokenArray);
        this.totalMoves = gameToBeCopied.totalMoves;
        this.players = gameToBeCopied.players;
    }

    public GameStatus checkHorizontalWin(Token token, int pcolumn) {

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 4; column++) {
                Set<String> tokens = new HashSet<>();
                tokens.add(this.currentBoard.getIndividualToken(row, column));
                tokens.add(this.currentBoard.getIndividualToken(row, column + 1));
                tokens.add(this.currentBoard.getIndividualToken(row, column + 2));
                tokens.add(this.currentBoard.getIndividualToken(row, column + 3));
                if (tokens.size() == 1 && !tokens.contains(" ")) {
                    return GameStatus.WON;
                }
            }
        }

        return GameStatus.PLAYING;
    }

    public GameStatus checkVerticalWin(Token token, int pcolumn) {
        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < 3; row++) {
                Set<String> tokens = new HashSet<>();
                tokens.add(this.currentBoard.getIndividualToken(row, column));
                tokens.add(this.currentBoard.getIndividualToken(row + 1, column));
                tokens.add(this.currentBoard.getIndividualToken(row + 2, column));
                tokens.add(this.currentBoard.getIndividualToken(row + 3, column));
                if (tokens.size() == 1 && !tokens.contains(" ")) {
                    return GameStatus.WON;
                }
            }
        }

        return GameStatus.PLAYING;
    }

    public GameStatus checkNegativeDiagonalWin(Token token, int pcolumn) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                Set<String> tokens = new HashSet<>();
                tokens.add(this.currentBoard.getIndividualToken(row, column));
                tokens.add(this.currentBoard.getIndividualToken(row + 1, column + 1));
                tokens.add(this.currentBoard.getIndividualToken(row + 2, column + 2));
                tokens.add(this.currentBoard.getIndividualToken(row + 3, column + 3));
                if (tokens.size() == 1 && !tokens.contains(" ")) {
                    return GameStatus.WON;
                }
            }
        }

        return GameStatus.PLAYING;
    }

    public GameStatus checkPositiveDiagonalWin(Token token, int pcolumn) {
        for (int row = 0; row < 3; row++) {
            for (int column = 6; column > 2; column--) {
                Set<String> tokens = new HashSet<>();
                tokens.add(this.currentBoard.getIndividualToken(row, column));
                tokens.add(this.currentBoard.getIndividualToken(row + 1, column - 1));
                tokens.add(this.currentBoard.getIndividualToken(row + 2, column - 2));
                tokens.add(this.currentBoard.getIndividualToken(row + 3, column - 3));
                if (tokens.size() == 1 && !tokens.contains(" ")) {
                    return GameStatus.WON;
                }
            }
        }

        return GameStatus.PLAYING;
    }

    public GameStatus determineGameStatus(Token token, int pcolumn) {
        if (checkHorizontalWin(token, pcolumn) == GameStatus.WON) {
            return GameStatus.WON;
        }
        if (checkVerticalWin(token, pcolumn) == GameStatus.WON) {
            return GameStatus.WON;
        }
        if (checkNegativeDiagonalWin(token, pcolumn) == GameStatus.WON) {
            return GameStatus.WON;
        }
        if (checkPositiveDiagonalWin(token, pcolumn) == GameStatus.WON) {
            return GameStatus.WON;
        } else if (totalMoves < 42) {
            return GameStatus.PLAYING;
        } else {
            return GameStatus.DRAWN;
        }
    }

    public GameStatus makeMove(Player player, int inputCol) {
        Board newBoard = currentBoard.makeMove(player.token, inputCol);
        if (newBoard == null) {
            return null;
        }
        currentBoard = newBoard;
        totalMoves += 1;
        return determineGameStatus(player.token, inputCol);
    }

    public int checkPossibleWinOnNextMove(Player player) {

        for (int column = 0; column < 7; column++) {
            Game copyOfGame = new Game(this);

            GameStatus status = copyOfGame.makeMove(player, column);
            if (status == GameStatus.WON) {
                return column;
            }
        }

        return -1;
    }

    public Set checkFork(Player player) {
        Set forkList = new HashSet<>();
        int computerCol = -1;
        for (int column = 0; column < 7; column++) {
            int foundWin = 0;
            Game copyOfGame = new Game(this);
            GameStatus status = copyOfGame.makeMove(player, column);
            if (status == null) {
                continue;
            }
            for (int column2 = 0; column2 < 7; column2++) {
                Game copyOfCopyOfGame = new Game(copyOfGame);
                status = copyOfCopyOfGame.makeMove(player, column2);
                if (status == GameStatus.WON) {
                    computerCol = column;
                    foundWin += 1;
                }
            }
            if (foundWin == 2) {
                forkList.add(computerCol);
            }
        }
        return forkList;
    }

    public int determineComputerMove(int pcolumn) {
        int col = checkPossibleWinOnNextMove(players[1]);
        if (col != -1) {
            return col + 1;
        }

        col = checkPossibleWinOnNextMove(players[0]);
        if (col != -1) {
            return col + 1;
        }

        Object[] forkList = checkFork(players[0]).toArray();
        if (forkList.length == 1) {

            return Integer.parseInt(forkList[0] + "") + 1;
        }
        if (forkList.length == 2) {
            System.out.println("here!");
            return Integer.parseInt(forkList[0] + "") + 1;
        }

        forkList = checkFork(players[1]).toArray();
        if (forkList.length == 1) {
            return Integer.parseInt(forkList[0] + "") + 1;
        }

        if (this.totalMoves == 1) {
            if (pcolumn == 2) {
                return 3;
            }
            if (pcolumn == 6) {
                return 5;
            } else {
                return 4;
            }
        }
        Random rand = new Random();
        int possibleCol = rand.nextInt(1, 8);
        if (this.currentBoard.getIndividualToken(0, possibleCol - 1).equals(" ")) {
            return possibleCol;
        } else {
            while (true) {
                possibleCol = rand.nextInt(1, 8);
                if (this.currentBoard.getIndividualToken(0, possibleCol - 1).equals(" ")) {
                    return possibleCol;
                }
            }
        }
    }

    public void draw(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setStroke(new BasicStroke(2.0f));

        g.setColor(new Color(48, 87, 230));
        g.fillRect(Connect4.widthUnit, Connect4.heightUnit + 25, Connect4.WIDTH - 2 * Connect4.widthUnit, Connect4.HEIGHT - 2 * Connect4.heightUnit);

        Polygon polygon = new Polygon();
        polygon.addPoint(Connect4.WIDTH - Connect4.widthUnit, Connect4.heightUnit + 25);
        polygon.addPoint(Connect4.WIDTH - Connect4.widthUnit + 10, Connect4.heightUnit + 15);
        polygon.addPoint(Connect4.WIDTH - Connect4.widthUnit + 10, Connect4.HEIGHT - Connect4.heightUnit + 15);
        polygon.addPoint(Connect4.WIDTH - Connect4.widthUnit, Connect4.HEIGHT - Connect4.heightUnit + 25);

        g.fillPolygon(polygon);

        Polygon polygon2 = new Polygon();
        polygon2.addPoint(Connect4.WIDTH - Connect4.widthUnit, Connect4.heightUnit + 25);
        polygon2.addPoint(Connect4.WIDTH - Connect4.widthUnit + 10, Connect4.heightUnit + 15);
        polygon2.addPoint(Connect4.widthUnit + 10, Connect4.heightUnit + 15);
        polygon2.addPoint(Connect4.widthUnit, Connect4.heightUnit + 25);

        g.fillPolygon(polygon2);

        g.setColor(Color.BLACK);

        Polygon polygon3 = new Polygon();
        polygon3.addPoint(Connect4.WIDTH - Connect4.widthUnit, Connect4.heightUnit + 25);
        polygon3.addPoint(Connect4.WIDTH - Connect4.widthUnit + 4, Connect4.heightUnit + 20);
        polygon3.addPoint(Connect4.widthUnit + 8, Connect4.heightUnit + 20);
        polygon3.addPoint(Connect4.widthUnit, Connect4.heightUnit + 25);

        g.fillPolygon(polygon3);

        g.drawPolygon(polygon);
        g.drawPolygon(polygon2);

        g.setColor(Color.BLACK);
        g.drawLine(Connect4.widthUnit, Connect4.heightUnit + 25, Connect4.widthUnit, Connect4.HEIGHT - Connect4.heightUnit + 25);
        g.drawLine(Connect4.WIDTH - Connect4.widthUnit, Connect4.heightUnit + 25, Connect4.WIDTH - Connect4.widthUnit, Connect4.HEIGHT - Connect4.heightUnit + 25);
        g.drawLine(Connect4.widthUnit, Connect4.HEIGHT - Connect4.heightUnit + 25, Connect4.WIDTH - Connect4.widthUnit, Connect4.HEIGHT - Connect4.heightUnit + 25);
        g.drawLine(Connect4.widthUnit, Connect4.heightUnit + 25, Connect4.WIDTH - Connect4.widthUnit, Connect4.heightUnit + 25);

        for (int i = Connect4.widthUnit; i <= Connect4.WIDTH - Connect4.widthUnit; i += Connect4.widthUnit) {

            if (i == Connect4.WIDTH - Connect4.widthUnit) {
                continue;
            }
            for (int j = Connect4.heightUnit; j < Connect4.HEIGHT - Connect4.heightUnit; j += Connect4.heightUnit) {

                if (this.currentBoard.getIndividualToken(j / Connect4.heightUnit - 1, i / Connect4.widthUnit - 1).equals(" ")) {
                    g.setColor(new Color(52, 210, 235));
                }
                if (this.currentBoard.getIndividualToken(j / Connect4.heightUnit - 1, i / Connect4.widthUnit - 1).equals("r")) {
                    g.setColor(Color.RED);
                }
                if (this.currentBoard.getIndividualToken(j / Connect4.heightUnit - 1, i / Connect4.widthUnit - 1).equals("y")) {
                    g.setColor(Color.YELLOW);
                }

                g.fillOval(i + 5, j + 30, Connect4.widthUnit - 10, Connect4.heightUnit - 10);
                g.setColor(Color.BLACK);
                g.drawOval(i + 5, j + 30, Connect4.widthUnit - 10, Connect4.heightUnit - 10);
            }

            if (determineGameStatus(this.players[0].token, -1) != GameStatus.PLAYING) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.fillOval(hoverX + 5, 5, Connect4.widthUnit - 10, Connect4.heightUnit - 10);
            g.setColor(Color.BLACK);
            g.drawOval(hoverX + 5, 5, Connect4.widthUnit - 10, Connect4.heightUnit - 10);
        }
    }

    public void hover(int x) {
        x -= x % Connect4.widthUnit;
        if (x < Connect4.widthUnit) {
            x = Connect4.widthUnit;
        }
        if (x >= Connect4.WIDTH - Connect4.widthUnit) {
            x = Connect4.WIDTH - 2 * Connect4.widthUnit;
        }
        this.hoverX = x;
    }

    public void drop(Boolean userClicked, Token token, int clickedX) {
        if (this.determineGameStatus(token, -1) == GameStatus.PLAYING) {

            if (userClicked && !finishedComputerMove) {
                return;
            }
            if (!this.currentBoard.isValidMove(clickedX / Connect4.widthUnit - 1)) {
                return;
            }
            if (userClicked) {
                finishedComputerMove = false;
            }
            new Thread(() -> {
                int x = clickedX;
                for (int i = 0; i < Connect4.boardHeight && this.currentBoard.getIndividualToken(i, x / Connect4.widthUnit - 1).equals(" "); i++) {
                    this.currentBoard.setIndividualToken(i, x / Connect4.widthUnit - 1, token);
                    try {
                        Thread.currentThread().sleep(100);
                    } catch (Exception ignored) {
                    }
                    this.currentBoard.setIndividualToken(i, x / Connect4.widthUnit - 1, new Token(" "));
                }
                this.currentBoard.makeMove(token, x / Connect4.widthUnit - 1);
                this.currentBoard.printBoard();
                if (!userClicked) {
                    finishedComputerMove = true;
                }

                if (userClicked && this.determineGameStatus(token, -1) == GameStatus.PLAYING) {
                    int computerCol = this.determineComputerMove(x / Connect4.widthUnit);
                    int clickedX2 = computerCol * Connect4.widthUnit;
                    drop(false, new Token("y"), clickedX2);
                }
            }).start();
        }
    }
}

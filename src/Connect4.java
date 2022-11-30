import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Connect4 extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    public static final int WIDTH, HEIGHT, widthUnit, heightUnit, boardLength, boardHeight;
    private static JFrame frame;
    private static Connect4 instance;
    private static Game newGame;
    private static Point p1, p2;

    public Connect4() {

        frame = new JFrame("Connect4");
        frame.setBounds(50, 50, WIDTH, HEIGHT);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        this.setBackground(new Color(52, 210, 235));

        javax.swing.Timer timer = new javax.swing.Timer(10, this);
        timer.start();

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }

    static {
        Player player1 = new Player("Joshua", new Token("r"));
        Player player2 = new Player("Computer", new Token("y"));
        newGame = new Game(player1, player2);
        int initialWidth = 1300;
        int initialHeight = 800;
        boardLength = 7;
        boardHeight = 6;
        widthUnit = initialWidth / (boardLength + 2);
        WIDTH = widthUnit * (boardLength + 2);
        heightUnit = initialHeight / (boardHeight + 2);
        HEIGHT = heightUnit * (boardHeight + 2);
    }

    public static void main(String[] args) {
        instance = new Connect4();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        newGame.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        newGame.hover(e.getX());
    }

    public void mousePressed(MouseEvent e) {
        newGame.drop(true, new Token("r"), newGame.hoverX);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
}

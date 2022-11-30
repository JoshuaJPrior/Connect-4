import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Connect4 extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    public static final int WIDTH, HEIGHT, widthUnit, heightUnit, boardLength, boardHeight;
    private static JFrame frame;
    private static Connect4 instance;
    private static Game newGame;
    private static Point p1, p2;

    public Connect4() {
        frame = new JFrame("Connect4");
        frame.setBounds(50, 50, WIDTH, HEIGHT);
        JPanel titleJPanel = new JPanel();
        titleJPanel.setBackground(new Color(52, 210, 235));
        titleJPanel.setVisible(true);
        titleJPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Connect 4");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 100));
        titleLabel.setBounds(250, 250, 450, 100);
        titleJPanel.add(titleLabel);

        frame.add(titleJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        this.setVisible(false);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {

        }
        frame.add(this);
        this.setVisible(true);

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

        int initialHeight = 800;
        boardLength = 7;
        boardHeight = 6;
        heightUnit = initialHeight / (boardHeight + 2);
        HEIGHT = heightUnit * (boardHeight + 2);
        widthUnit = heightUnit;
        WIDTH = widthUnit * (boardLength + 2);
        ;
    }

    public static void main(String[] args) {
        instance = new Connect4();
    }

    public void actionPerformed(ActionEvent e) {
        super.repaint();
    }

    public void paintComponent(Graphics g) {
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

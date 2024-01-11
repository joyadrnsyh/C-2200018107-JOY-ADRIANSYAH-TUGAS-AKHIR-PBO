import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTT1 extends JFrame implements ActionListener {
    private int a[][] = {
            { 10, 1, 2, 3, 11 },
            { 10, 1, 4, 7, 11 },
            { 10, 1, 5, 9, 11 },
            { 10, 2, 5, 8, 11 },
            { 10, 3, 5, 7, 11 },
            { 10, 3, 6, 9, 11 },
            { 10, 4, 5, 6, 11 },
            { 10, 7, 8, 9, 11 }
    };
    private Icon ic1, ic2, ic11, ic22;
    private JButton b[] = new JButton[9];
    private JButton reset;
    private JRadioButtonMenuItem vsKomputerMenuItem;
    private JRadioButtonMenuItem vsTemanMenuItem;
    private JLabel scoreLabel;
    private int playerScore = 0;
    private int computerScore = 0;
    private boolean state;
    private String playerName;

    public void showButton() {
        int x = 10;
        int y = 10;
        int j = 0;

        for (int i = 0; i <= 8; i++, x += 100, j++) {
            b[i] = new JButton();
            if (j == 3) {
                j = 0;
                y += 100;
                x = 10;
            }
            b[i].setBounds(x, y, 100, 100);
            b[i].setFont(new Font("Serif", Font.PLAIN, 40));
            b[i].setBackground(Color.BLACK);
            add(b[i]);
            b[i].addActionListener(this);
        }

        reset = new JButton("ULANG");
        reset.setBounds(100, 350, 100, 50);
        reset.setFont(new Font("Arial", Font.PLAIN, 18));
        add(reset);
        reset.addActionListener(this);

        scoreLabel = new JLabel("PLAYER 1 : 0 | PLAYER 2 : 0");
        scoreLabel.setBounds(10, 300, 400, 30);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        JLabel backgroundLabel = new JLabel();
        ImageIcon backgroundImage = new ImageIcon("BG.jpg");
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 500);
        add(backgroundLabel);
    }

    public TTT1() {
        super("Tic Tac Toe by Joy Adriansyah");
        getContentPane().setBackground(new Color(0xFAEF5D));

        ic1 = new ImageIcon("ic1.jpg");
        ic2 = new ImageIcon("ic2.jpg");
        ic11 = new ImageIcon("ic11.jpg");
        ic22 = new ImageIcon("ic22.jpg");

        vsKomputerMenuItem = new JRadioButtonMenuItem("vs Komputer");
        vsTemanMenuItem = new JRadioButtonMenuItem("vs Teman");
        vsKomputerMenuItem.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(vsKomputerMenuItem);
        group.add(vsTemanMenuItem);

        JMenu modeMenu = new JMenu("Mode Permainan");
        modeMenu.add(vsKomputerMenuItem);
        modeMenu.add(vsTemanMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(modeMenu);

        setJMenuBar(menuBar);

        showButton();

        setSize(400, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (vsKomputerMenuItem.isSelected()) {
            if (e.getSource() == reset) {
                for (JButton button : b) {
                    button.setIcon(null);
                }
            } else {
                for (int i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i] && b[i].getIcon() == null) {
                        b[i].setIcon(ic1);
                        checkWin(ic1, "Kamu");
                        complogic(i);
                    }
                }
            }
        } else if (vsTemanMenuItem.isSelected()) {
            if (e.getSource() == reset) {
                for (JButton button : b) {
                    button.setIcon(null);
                }
            } else {
                for (int i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i] && b[i].getIcon() == null) {
                        b[i].setIcon(state ? ic1 : ic2);
                        state = !state;
                        checkWin(ic1, "Player 1");
                        checkWin(ic2, "Player 2");
                    }
                }
            }
        }
    }

    private void checkWin(Icon playerIcon, String playerName) {
        for (int i = 0; i <= 7; i++) {
            int[] positions = { a[i][1], a[i][2], a[i][3] };
            if (b[positions[0] - 1].getIcon() == playerIcon &&
                    b[positions[1] - 1].getIcon() == playerIcon &&
                    b[positions[2] - 1].getIcon() == playerIcon) {
                for (int pos : positions) {
                    b[pos - 1].setIcon(playerIcon == ic1 ? ic11 : ic22);
                }

                JOptionPane.showMessageDialog(TTT1.this,
                        "Selamat, " + playerName + "! Kamu Menang, Klik Buat Ulang Ya:).");

                if (playerIcon == ic1) {
                    playerScore++;
                } else {
                    computerScore++;
                }
                updateScores();
                break;
            }
        }
    }

    /**
     * @param num
     */
    private void complogic(int num) {
        for (int i = 0; i <= 7; i++) {
            for (int j = 1; j <= 3; j++) {
                if (a[i][j] == num) {
                    a[i][0] = 11;
                    a[i][4] = 10;
                }
            }
        }
        for (int i = 0; i <= 7; i++) {
            boolean set = true;
            if (a[i][4] == 10) {
                int count = 0;
                int yesnull = 0;
                for (int j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getIcon() != null) {
                        count++;
                    } else {
                        yesnull = a[i][j];
                    }
                }
                if (count == 2) {
                    b[yesnull - 1].setIcon(ic2);
                    checkWin(ic2, "Player");
                    set = false;
                    break;
                }
            } else if (a[i][0] == 10) {
                for (int j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getIcon() == null) {
                        b[(a[i][j] - 1)].setIcon(ic2);
                        checkWin(ic2, "Player");
                        set = false;
                        break;
                    }
                }
                if (!set) {
                    break;
                }
            }
            if (!set) {
                break;
            }
        }
    }

    private void updateScores() {
        scoreLabel.setText("PLAYER 1: " + playerScore + " | PLAYER 2: " + computerScore);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TTT1());
    }
}

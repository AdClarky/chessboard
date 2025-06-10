package window;

import ai.RandomMoves;
import ai.Robot;
import chessboard.ChessInterface;
import common.PieceColour;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Chess Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(Color.GRAY);
        mainMenuPanel.setLayout(new GridBagLayout());
        add(mainMenuPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Font buttonFont = new Font("Arial", Font.BOLD, 28);
        Dimension buttonSize = new Dimension(300, 70);

        JButton btnComputer = createStyledButton("Against Computer", buttonFont, buttonSize);
        btnComputer.addActionListener(e -> startVsComputer());
        gbc.gridy = 0;
        mainMenuPanel.add(btnComputer, gbc);

        JButton btnLocalVs = createStyledButton("Local Vs", buttonFont, buttonSize);
        btnLocalVs.addActionListener(e -> startVsLocal());
        gbc.gridy = 1;
        mainMenuPanel.add(btnLocalVs, gbc);

        JButton btnOnlineVs = createStyledButton("Online Vs", buttonFont, buttonSize);
        btnOnlineVs.addActionListener(e -> startVsOnline());
        gbc.gridy = 2;
        mainMenuPanel.add(btnOnlineVs, gbc);

        JButton btnQuit = createStyledButton("Quit", buttonFont, buttonSize);
        btnQuit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        gbc.gridy = 3;
        mainMenuPanel.add(btnQuit, gbc);

        pack();
        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 3),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 205, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113));
            }
        });
        return button;
    }

    private void startVsComputer(){
        ChessInterface chessGame = new ChessInterface();
        GameWindow whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(whiteWindow);
        Robot blackRobot = new Robot(chessGame, PieceColour.BLACK, new RandomMoves());
        chessGame.addBoardListener(blackRobot);

    }

    private void startVsLocal(){
        ChessInterface chessGame = new ChessInterface();
        GameWindow whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(whiteWindow);
        GameWindow blackWindow = new GameWindow(chessGame, PieceColour.BLACK);
        chessGame.addBoardListener(blackWindow);

        blackWindow.setLocation(whiteWindow.getSize().width, 0);
    }

    private void startVsOnline(){

    }
}

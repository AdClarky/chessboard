package window;

import ai.MoveChooser;
import ai.RandomMoves;
import ai.TakingMove;
import chessboard.ChessGame;
import common.PieceColour;
import exception.InvalidFenStringException;

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

        JButton vsComputerButton = createStyledButton("Against Computer", buttonFont, buttonSize);
        vsComputerButton.addActionListener(e -> startVsComputer());
        gbc.gridy = 0;
        mainMenuPanel.add(vsComputerButton, gbc);

        JButton computerVsComputerButton = createStyledButton("PC vs PC", buttonFont, buttonSize);
        computerVsComputerButton.addActionListener(e -> startComputerVsComputer());
        gbc.gridy = 1;
        mainMenuPanel.add(computerVsComputerButton, gbc);

        JButton vsLocalButton = createStyledButton("Local Vs", buttonFont, buttonSize);
        vsLocalButton.addActionListener(e -> startVsLocal());
        gbc.gridy = 2;
        mainMenuPanel.add(vsLocalButton, gbc);

        JButton vsOnlineButton = createStyledButton("Online Vs", buttonFont, buttonSize);
        vsOnlineButton.addActionListener(e -> startVsOnline());
        gbc.gridy = 3;
        mainMenuPanel.add(vsOnlineButton, gbc);

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

    private void startComputerVsComputer(){
        ChessGame chessGame = new ChessGame();
        GameWindow whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(whiteWindow);
        MoveChooser whiteChooser = new TakingMove();
        MoveChooser blackChooser = new RandomMoves();

        new GameWorker(chessGame, whiteChooser, blackChooser).execute();
    }

    private void startVsComputer(){
        ChessGame chessGame = null;
        try {
            chessGame = new ChessGame("8/PPPPPPPP/8/8/k6K/8/pppppppp/8 w - - 0 1");
        } catch (InvalidFenStringException e) {
            throw new RuntimeException(e);
        }
        GameWindow whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(whiteWindow);
        MoveChooser blackChooser = new TakingMove();

        new GameWorker(chessGame, whiteWindow, blackChooser).execute();
    }

    private void startVsLocal(){
        ChessGame chessGame = new ChessGame();
        GameWindow whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(whiteWindow);
        GameWindow blackWindow = new GameWindow(chessGame, PieceColour.BLACK);
        chessGame.addBoardListener(blackWindow);

        blackWindow.setLocation(whiteWindow.getSize().width, 0);

        new GameWorker(chessGame, whiteWindow, blackWindow).execute();
    }

    private void startVsOnline(){

    }
}

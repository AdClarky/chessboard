package window;

import common.PieceColour;
import common.PieceValue;
import common.Pieces;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PromotionWindow extends JDialog {
    private PieceValue selectedPiece;

    public PromotionWindow(JFrame parent, PieceColour colour) {
        super(parent, "Promote Pawn", true);
        setLayout(new GridLayout(1, 4));

        PieceValue[] pieces = {
                new PieceValue(null, Pieces.QUEEN, colour),
                new PieceValue(null, Pieces.ROOK, colour),
                new PieceValue(null, Pieces.KNIGHT, colour),
                new PieceValue(null, Pieces.BISHOP, colour)
        };
        for (PieceValue piece : pieces) {
            JButton button = new JButton(ImageUtils.getPieceImage(piece));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setContentAreaFilled(false);
            button.addActionListener(e -> {
                selectedPiece = piece;
                dispose();
            });

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setIcon(new ImageIcon(ImageUtils.getPieceImage(piece).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setIcon(ImageUtils.getPieceImage(piece));
                }
            });
            add(button);
        }

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public Pieces getSelectedPiece() {
        return selectedPiece.pieceType();
    }
}